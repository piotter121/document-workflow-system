package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.io.TikaInputStream;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.FileMetadataRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.data.repository.VersionRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileContentDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.FileNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.services.DifferenceService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.TikaService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.VersionService;
import pl.edu.pw.ee.pyskp.documentworkflow.services.events.VersionCreatedEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 06.01.17.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VersionServiceImpl implements VersionService {
    private static final String DEFAULT_MESSAGE = "Dodanie pliku";

    @NonNull
    private final UserService userService;

    @NonNull
    private final DifferenceService differenceService;

    @NonNull
    private final VersionRepository versionRepository;

    @NonNull
    private final FileMetadataRepository fileMetadataRepository;

    @NonNull
    private final ApplicationEventPublisher applicationEventPublisher;

    @NonNull
    private final TikaService tikaService;

    private final MessageDigest sha256 = initializeSHA256();

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
    public Version createInitVersionOfFile(NewFileForm form, FileMetadata fileMetadata) {
        try {
            Version version = new Version();
            version.setFile(fileMetadata);
            version.setSaveDate(new Date());
            version.setVersionString(form.getVersionString());
            version.setMessage(DEFAULT_MESSAGE);
            version.setAuthor(userService.getCurrentUser());
            MultipartFile file = form.getFile();
            byte[] bytes = file.getBytes();
            version.setFileContent(bytes);

            List<String> parsedContentLines = tikaService.extractLines(TikaInputStream.get(bytes));
            version.setParsedFileContent(parsedContentLines);

            version.setCheckSum(calculateCheckSum(bytes));

            List<Difference> differences = differenceService.createDifferencesForNewFile(parsedContentLines);
            version.setDifferences(differences);
            return versionRepository.save(version);
        } catch (IOException e) {
            log.error("Input/output exception during getBytes from multipartFile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InputStream getVersionFileContent(ObjectId fileId, Date saveDate) throws VersionNotFoundException {
        Version version = versionRepository.findOneByFile_IdAndSaveDate(fileId, saveDate)
                .orElseThrow(() -> new VersionNotFoundException(String.valueOf(saveDate.getTime())));
        return new ByteArrayInputStream(version.getFileContent());
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public long addNewVersionOfFile(NewVersionForm form) throws ResourceNotFoundException {
        try {
            FileMetadata fileMetadata = getFileMetadata(form.getFileId());
            Version newVersion = new Version();
            newVersion.setFile(fileMetadata);
            newVersion.setSaveDate(new Date());
            newVersion.setVersionString(form.getVersionString());
            newVersion.setMessage(form.getMessage());
            newVersion.setAuthor(userService.getCurrentUser());

            byte[] file = form.getFile().getBytes();
            newVersion.setFileContent(file);

            List<String> newVersionLines = tikaService.extractLines(TikaInputStream.get(file));
            newVersion.setParsedFileContent(newVersionLines);

            newVersion.setCheckSum(calculateCheckSum(file));

            List<String> previousVersionLines = versionRepository.findTopByFileOrderBySaveDateDesc(fileMetadata)
                    .map(Version::getParsedFileContent)
                    .orElseThrow(VersionNotFoundException::new);
            List<Difference> differences =
                    differenceService.getDifferencesBetweenTwoFiles(previousVersionLines, newVersionLines);
            newVersion.setDifferences(differences);

            newVersion = versionRepository.save(newVersion);

            applicationEventPublisher.publishEvent(new VersionCreatedEvent(this, newVersion, fileMetadata));

            return newVersion.getSaveDate().getTime();
        } catch (IOException e) {
            log.error("Input/output exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DiffData buildDiffData(ObjectId fileId, long versionSaveDateMillis) throws VersionNotFoundException {
        List<Version> last2Versions = versionRepository
                .findByFile_IdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, new Date(versionSaveDateMillis));
        if (last2Versions.isEmpty())
            throw new VersionNotFoundException(String.valueOf(versionSaveDateMillis));
        Version currentVersion = last2Versions.get(0);
        FileContentDTO oldContent = null;
        if (last2Versions.size() != 1) {
            Version previousVersion = last2Versions.get(1);
            oldContent = new FileContentDTO(previousVersion.getParsedFileContent());
        }
        return new DiffData(
                currentVersion.getDifferences(),
                new FileContentDTO(currentVersion.getParsedFileContent()),
                oldContent
        );
    }

    @Override
    @Transactional(readOnly = true)
    public VersionInfoDTO getVersionInfo(ObjectId fileId, long versionSaveDateMillis) throws VersionNotFoundException {
        List<Version> versions = versionRepository
                .findByFile_IdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, new Date(versionSaveDateMillis));
        if (versions.isEmpty())
            throw new VersionNotFoundException(String.valueOf(versionSaveDateMillis));
        String previousVersionString = null;
        if (versions.size() >= 2) {
            Version previousVersion = versions.get(1);
            previousVersionString = previousVersion.getVersionString();
        }
        Version currentVersion = versions.get(0);
        return VersionInfoDTO.fromVersion(currentVersion, previousVersionString);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByVersionString(ObjectId fileId, String versionString) {
        return versionRepository.existsByFile_IdAndVersionString(fileId, versionString);
    }

    private FileMetadata getFileMetadata(ObjectId fileId) throws FileNotFoundException {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

}
