package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.FileContentDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.DifferenceService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FileContentService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.VersionService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 06.01.17.
 */
@Service
public class VersionServiceImpl implements VersionService {
    private static final Logger logger = Logger.getLogger(VersionServiceImpl.class);

    private final UserService userService;
    private final DifferenceService differenceService;
    private final VersionRepository versionRepository;
    private final FileMetadataRepository fileMetadataRepository;

    public VersionServiceImpl(UserService userService,
                              DifferenceService differenceService,
                              VersionRepository versionRepository,
                              FileMetadataRepository fileMetadataRepository) {
        this.userService = userService;
        this.differenceService = differenceService;
        this.versionRepository = versionRepository;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @Override
    public Version createUnmanagedInitVersionOfFile(NewFileForm form) throws IOException, TikaException {
        Version version = new Version();
        version.setVersionString(form.getVersionString());
        version.setMessage(form.getVersionMessage());
        version.setAuthor(userService.getCurrentUser());
        MultipartFile file = form.getFile();
        version.setCheckSum(calculateCheckSum(file.getBytes()));
        version.setSaveDate(new Date());
        version.setFileContent(getFileContent(file));
        List<Difference> differences = differenceService.createDifferencesForNewFile(file.getInputStream());
        version.setDifferences(differences);
        differences.forEach(difference -> difference.setVersion(version));
        return version;
    }

    @Override
    public Optional<Version> getOneById(long versionId) {
        return Optional.ofNullable(versionRepository.findOne(versionId));
    }

    @Override
    public Optional<FileContentDTO> getPreviousVersionContentDTO(long versionId) {
        return getOneById(versionId)
                .orElseThrow(() -> new VersionNotFoundException(versionId))
                .getPreviousVersion()
                .map(Version::getFileContent)
                .map(FileContentService::mapToFileContentDTO);
    }

    @Override
    public Version addNewVersionOfFile(NewVersionForm form) throws IOException {
        Version newVersion = new Version();
        MultipartFile file = form.getFile();
        newVersion.setSaveDate(new Date());
        newVersion.setAuthor(userService.getCurrentUser());
        newVersion.setVersionString(form.getVersionString());
        newVersion.setMessage(form.getMessage());
        newVersion.setCheckSum(calculateCheckSum(file.getBytes()));
        newVersion.setFileContent(getFileContent(file));
        FileMetadata fileMetadata = getFileMetadata(form.getFileId());
        newVersion.setFileMetadata(fileMetadata);
        FileContent oldContent = fileMetadata.getLatestVersion().getFileContent();
        List<Difference> differences = differenceService.getDifferencesBetweenTwoFiles(
                new ByteArrayInputStream(oldContent.getContent()), file.getInputStream());
        differences.forEach(difference -> difference.setVersion(newVersion));
        newVersion.setDifferences(differences);
        return versionRepository.saveAndFlush(newVersion);
    }

    @Override
    public DiffData buildDiffData(long versionId) {
        Version version = getOneById(versionId).orElseThrow(() -> new VersionNotFoundException(versionId));
        Optional<Version> previousVersionOpt = version.getPreviousVersion();
        DiffData diffData = new DiffData();
        diffData.setDifferences(version.getDifferences());
        diffData.setNewContent(FileContentService.mapToFileContentDTO(version.getFileContent()));
        previousVersionOpt.ifPresent(previousVersion ->
                diffData.setOldContent(
                        FileContentService.mapToFileContentDTO(previousVersion.getFileContent())));
        return diffData;
    }

    private FileMetadata getFileMetadata(long fileId) {
        return Optional.ofNullable(fileMetadataRepository.findOne(fileId))
                .orElseThrow(() -> new FileNotFoundException(fileId));
    }

    private static FileContent getFileContent(MultipartFile file) throws IOException {
        FileContent fileContent = new FileContent();
        fileContent.setContent(file.getBytes());
        return fileContent;
    }

    private static String calculateCheckSum(byte[] bytes) {
        try {
            return String.format("%064x",
                    new BigInteger(MessageDigest.getInstance("SHA-256").digest(bytes)));
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
