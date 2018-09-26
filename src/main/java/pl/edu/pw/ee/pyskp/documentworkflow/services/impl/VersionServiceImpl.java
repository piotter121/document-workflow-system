package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.*;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.DifferenceService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TikaService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class VersionServiceImpl implements VersionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionServiceImpl.class);

    @NonNull
    private final UserService userService;

    @NonNull
    private final DifferenceService differenceService;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final TikaService tikaService;

    private final MessageDigest sha256 = initializeSHA256();

    private MessageDigest initializeSHA256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private String calculateCheckSum(byte[] bytes) {
        return String.format("%064x", new BigInteger(sha256.digest(bytes)));
    }

    @Override
    @Transactional
    public void createInitVersionOfFile(NewFileForm form, FileMetadata fileMetadata) {
        try {
            Version version = new Version();
            version.setFileId(fileMetadata.getId());
            version.setSaveDate(getNowTimestamp());
            version.setVersionString(form.getVersionString());
            version.setMessage(form.getVersionMessage());
            version.setAuthor(new UserSummary(userService.getCurrentUser()));
            MultipartFile file = form.getFile();
            byte[] bytes = file.getBytes();
            version.setCheckSum(calculateCheckSum(bytes));
            ByteBuffer content = ByteBuffer.wrap(bytes);
            version.setFileContent(content);
            List<Difference> differences = differenceService.createDifferencesForNewFile(file.getInputStream());
            version.setDifferences(differences);

            versionRepository.save(version);
        } catch (IOException e) {
            LOGGER.error("Input/output exception during getBytes from multipartFile", e);
            throw new RuntimeException(e);
        }
    }

    private Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream getVersionFileContent(long fileId, final Date saveDate)
            throws VersionNotFoundException {
        Version version = versionRepository.findOneByFileIdAndSaveDate(fileId, saveDate)
                .orElseThrow(() -> new VersionNotFoundException(saveDate.toString()));
        return new ByteArrayInputStream(version.getFileContent().array());
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public Date addNewVersionOfFile(NewVersionForm form) throws ResourceNotFoundException {
        try {
            Version newVersion = new Version();
            newVersion.setSaveDate(new Date());
            User modificationAuthor = userService.getCurrentUser();
            newVersion.setAuthor(new UserSummary(modificationAuthor));
            newVersion.setVersionString(form.getVersionString());
            newVersion.setMessage(form.getMessage());
            byte[] file = form.getFile().getBytes();
            newVersion.setCheckSum(calculateCheckSum(file));
            newVersion.setFileContent(ByteBuffer.wrap(file));
            FileMetadata fileMetadata = getFileMetadata(form.getFileId());
            newVersion.setFileId(form.getFileId());
            byte[] oldContent = versionRepository.findTopByFileIdOrderBySaveDateDesc(form.getFileId())
                    .map(version -> version.getFileContent().array())
                    .orElseThrow(VersionNotFoundException::new);
            List<Difference> differences = differenceService.getDifferencesBetweenTwoFiles(
                    new ByteArrayInputStream(oldContent), new ByteArrayInputStream(file)
            );
            newVersion.setDifferences(differences);
            newVersion = versionRepository.save(newVersion);
            VersionSummary versionSummary = new VersionSummary(newVersion.getVersionString(),
                    convert(newVersion.getSaveDate()), modificationAuthor);
            fileMetadata.setLatestVersion(versionSummary);
            fileMetadataRepository.save(fileMetadata);

            return newVersion.getSaveDate();
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    private Timestamp convert(Date date) {
        return new Timestamp(date.getTime());
    }

    @Override
    @Transactional(readOnly = true)
    public DiffData buildDiffData(long fileId, Date versionSaveDate) throws VersionNotFoundException {
        List<Version> last2Versions = versionRepository
                .findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, versionSaveDate);
        if (last2Versions.isEmpty()) {
            throw new VersionNotFoundException(versionSaveDate.toString());
        }

        Version version = last2Versions.get(0);
        FileContentDTO newContent = new FileContentDTO(getLines(version));
        FileContentDTO oldContent = null;

        if (last2Versions.size() > 1) {
            oldContent = new FileContentDTO(getLines(last2Versions.get(1)));
        }

        List<Difference> differences = version.getDifferences() == null ?
                Collections.emptyList() : version.getDifferences();
        return new DiffData(differences, newContent, oldContent);
    }

    private List<String> getLines(Version version) {
        byte[] bytes = version.getFileContent().array();
        try {
            return tikaService.extractLines(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred during extraction of lines");
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public VersionInfoDTO getVersionInfo(long fileId, Date versionSaveDate) throws VersionNotFoundException {
        List<Version> versions = versionRepository
                .findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, versionSaveDate);
        if (versions.isEmpty()) {
            throw new VersionNotFoundException(versionSaveDate.toString());
        }

        String previousVersionString = null;
        if (versions.size() == 2) {
            previousVersionString = versions.get(1).getVersionString();
        }

        Version version = versions.get(0);
        return new VersionInfoDTO(
                version.getMessage(),
                UserInfoDTO.fromUserSummary(version.getAuthor()),
                version.getSaveDate(),
                version.getVersionString(),
                previousVersionString,
                getDifferencesDTOs(version)
        );
    }

    private List<DifferenceInfoDTO> getDifferencesDTOs(Version version) {
        if (version.getDifferences() == null) {
            return Collections.emptyList();
        }
        return version.getDifferences()
                .stream()
                .map(difference -> new DifferenceInfoDTO(
                        difference.getPreviousSectionStart(),
                        difference.getPreviousSectionSize(),
                        difference.getNewSectionStart(),
                        difference.getNewSectionSize(),
                        difference.getDifferenceType()
                )).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByVersionString(long fileId, String versionString) {
        return versionRepository.existsByFileIdAndVersionString(fileId, versionString);
    }

    @Override
    @Transactional
    public void deleteProjectFilesVersions(long projectId) {
        List<Long> fileIds = fileMetadataRepository.findIdByTask_Project_Id(projectId);
        if (!fileIds.isEmpty()) {
            versionRepository.deleteAllByFileIdIn(fileIds);
        }
    }

    @Override
    @Transactional
    public void deleteTaskFilesVersions(long taskId) {
        List<Long> fileIds = fileMetadataRepository.findIdByTask_Id(taskId);
        if (!fileIds.isEmpty()) {
            versionRepository.deleteAllByFileIdIn(fileIds);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getNumberOfVersions(FileMetadata fileMetadata) {
        return versionRepository.countDistinctByFileId(fileMetadata.getId());
    }

    private FileMetadata getFileMetadata(long fileId) throws FileNotFoundException {
        FileMetadata fileMetadata = fileMetadataRepository.findOne(fileId);
        if (fileMetadata == null) {
            throw new FileNotFoundException(String.valueOf(fileId));
        }
        return fileMetadata;
    }


}
