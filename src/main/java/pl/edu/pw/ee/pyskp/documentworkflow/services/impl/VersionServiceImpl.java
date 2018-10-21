package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.*;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileContentDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DifferenceInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.events.FileChangedEvent;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class VersionServiceImpl implements VersionService {
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

    @Setter
    private ApplicationEventPublisher applicationEventPublisher;

    private MessageDigest initializeSHA256() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private String calculateCheckSum(byte[] bytes) {
        return String.format("%064x", new BigInteger(sha256.digest(bytes)));
    }

    @Override
    @Transactional
    public void createInitVersionOfFile(NewFileForm form, UUID fileId) {
        try {
            Version version = new Version();
            version.setFileId(fileId);
            version.setSaveDate(new Date());
            version.setVersionString(form.getVersionString());
            version.setMessage(form.getVersionMessage());
            version.setAuthor(new UserSummary(userService.getCurrentUser()));

            MultipartFile file = form.getFile();
            byte[] bytes = file.getBytes();
            ByteBuffer content = ByteBuffer.wrap(bytes);
            version.setFileContent(content);
            List<String> newVersionLines = tikaService.extractLines(new ByteArrayInputStream(bytes));
            version.setParsedFileContent(String.join("\r\n", newVersionLines));
            version.setCheckSum(calculateCheckSum(bytes));
            List<Difference> differences = differenceService.createDifferencesForNewFile(newVersionLines);
            version.setDifferences(differences);
            versionRepository.save(version);
        } catch (IOException e) {
            log.error("Input/output exception during getBytes from multipartFile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream getVersionFileContent(UUID fileId, Date saveDate) throws VersionNotFoundException {
        Version version = versionRepository.findOneByFileIdAndSaveDate(fileId, saveDate)
                .orElseThrow(() -> new VersionNotFoundException(String.valueOf(saveDate.getTime())));
        return new ByteArrayInputStream(version.getFileContent().array());
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public long addNewVersionOfFile(NewVersionForm form) throws ResourceNotFoundException {
        try {
            UUID fileId = form.getFileId();
            Version newVersion = new Version();
            newVersion.setFileId(fileId);
            newVersion.setSaveDate(new Date());
            newVersion.setVersionString(form.getVersionString());
            newVersion.setMessage(form.getMessage());

            UserSummary author = new UserSummary(userService.getCurrentUser());
            newVersion.setAuthor(author);

            byte[] file = form.getFile().getBytes();
            newVersion.setFileContent(ByteBuffer.wrap(file));

            List<String> newVersionLines = tikaService.extractLines(new ByteArrayInputStream(file));
            newVersion.setParsedFileContent(String.join("\r\n", newVersionLines));

            newVersion.setCheckSum(calculateCheckSum(file));
            ByteArrayInputStream previousVersionContent = versionRepository
                    .findTopByFileIdOrderBySaveDateDesc(fileId)
                    .map(version -> new ByteArrayInputStream(version.getFileContent().array()))
                    .orElseThrow(() -> new VersionNotFoundException(fileId.toString()));
            List<String> previousVersionLines = tikaService.extractLines(previousVersionContent);

            List<Difference> differences =
                    differenceService.getDifferencesBetweenTwoFiles(previousVersionLines, newVersionLines);
            newVersion.setDifferences(differences);

            final long saveDateTime = versionRepository.save(newVersion).getSaveDate().getTime();

            UUID taskId = form.getTaskId();
            FileMetadata fileMetadata = getFileMetadata(taskId, fileId);
            fileMetadata.setLatestVersion(new VersionSummary(newVersion));
            fileMetadata.setNumberOfVersions(fileMetadata.getNumberOfVersions() + 1);
            fileMetadataRepository.save(fileMetadata);

            UUID projectId = form.getProjectId();
            applicationEventPublisher.publishEvent(new FileChangedEvent(this, projectId, taskId, fileId));

            return saveDateTime;
        } catch (IOException e) {
            log.error("Input/output exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DiffData buildDiffData(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException {
        List<Version> last2Versions = versionRepository
                .findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, new Date(versionSaveDateMillis));
        if (last2Versions.isEmpty()) {
            throw new VersionNotFoundException(String.valueOf(versionSaveDateMillis));
        }
        Version currentVersion = last2Versions.get(0);

        FileContentDTO newContent = new FileContentDTO(getLines(currentVersion));

        FileContentDTO oldContent = null;
        if (last2Versions.size() != 1) {
            Version previousVersion = last2Versions.get(1);
            oldContent = new FileContentDTO(getLines(previousVersion));
        }

        return new DiffData(
                currentVersion.getDifferences(),
                newContent,
                oldContent
        );
    }

    private List<String> getLines(Version version) {
        return Arrays.asList(version.getParsedFileContent().split("\r\n"));
    }

    @Override
    @Transactional(readOnly = true)
    public VersionInfoDTO getVersionInfo(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException {
        List<Version> versions = versionRepository
                .findTop2ByFileIdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, new Date(versionSaveDateMillis));
        if (versions.isEmpty()) {
            throw new VersionNotFoundException(String.valueOf(versionSaveDateMillis));
        }
        Version currentVersion = versions.get(0);
        String previousVersionString = "";
        if (versions.size() == 2) {
            Version previousVersion = versions.get(1);
            previousVersionString = previousVersion.getVersionString();
        }
        List<DifferenceInfoDTO> differences = currentVersion.getDifferences()
                .stream()
                .map(DifferenceInfoDTO::fromDifference)
                .collect(Collectors.toList());
        return new VersionInfoDTO(
                currentVersion.getMessage(),
                UserInfoDTO.fromUserSummary(currentVersion.getAuthor()),
                currentVersion.getSaveDate().getTime(),
                currentVersion.getVersionString(),
                previousVersionString,
                differences
        );
    }

    @Override
    public boolean existsByVersionString(UUID fileId, String versionString) {
        return versionRepository.findAllByFileId(fileId)
                .anyMatch(version -> versionString.equals(version.getVersionString()));
    }

    private FileMetadata getFileMetadata(UUID taskId, UUID fileId) throws FileNotFoundException {
        return fileMetadataRepository.findOneByTaskIdAndFileId(taskId, fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }


}
