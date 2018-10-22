package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.function.Predicate;

/**
 * Created by piotr on 06.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class VersionServiceImpl implements VersionService {
    private static final String DEFAULT_MESSAGE = "Dodanie pliku";
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
    private final ApplicationEventPublisher applicationEventPublisher;

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
    public Version createInitVersionOfFile(NewFileForm form) {
        try {
            Version version = new Version();
            version.setVersionString(form.getVersionString());
            version.setMessage(DEFAULT_MESSAGE);
            version.setAuthor(userService.getCurrentUser());
            MultipartFile file = form.getFile();
            byte[] bytes = file.getBytes();
            version.setCheckSum(calculateCheckSum(bytes));
            version.setSaveDate(new Date());
            version.setFileContent(bytes);
            List<Difference> differences = differenceService.createDifferencesForNewFile(file.getInputStream());
            version.setDifferences(differences);
            return version;
        } catch (IOException e) {
            LOGGER.error("Input/output exception during getBytes from multipartFile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getVersionFileContent(ObjectId fileId, Date saveDate) throws VersionNotFoundException {
        Version version = versionRepository.findOneByFile_IdAndSaveDate(fileId, saveDate)
                .orElseThrow(() -> new VersionNotFoundException(String.valueOf(saveDate.getTime())));
        return new ByteArrayInputStream(version.getFileContent());
    }

    @Override
    @Transactional
    public long addNewVersionOfFile(NewVersionForm form) throws ResourceNotFoundException {
        try {
            Version newVersion = new Version();
            byte[] file = form.getFile().getBytes();
            newVersion.setSaveDate(new Date());
            newVersion.setAuthor(userService.getCurrentUser());
            newVersion.setVersionString(form.getVersionString());
            newVersion.setMessage(form.getMessage());
            newVersion.setCheckSum(calculateCheckSum(file));
            newVersion.setFileContent(file);
            FileMetadata fileMetadata = getFileMetadata(form.getFileId());
            newVersion.setFile(fileMetadata);
            byte[] oldContent = versionRepository.findTopByFileOrderBySaveDateDesc(fileMetadata)
                    .map(Version::getFileContent)
                    .orElseThrow(VersionNotFoundException::new);
            List<Difference> differences = differenceService.getDifferencesBetweenTwoFiles(
                    new ByteArrayInputStream(oldContent), new ByteArrayInputStream(file));
            newVersion.setDifferences(differences);
            newVersion = versionRepository.save(newVersion);

            applicationEventPublisher.publishEvent(new VersionCreatedEvent(this, newVersion, fileMetadata));

            return newVersion.getSaveDate().getTime();
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public DiffData buildDiffData(ObjectId fileId, long versionSaveDateMillis) throws VersionNotFoundException {
        List<Version> last2Versions = versionRepository
                .findByFile_IdAndSaveDateLessThanEqualOrderBySaveDateDesc(fileId, new Date(versionSaveDateMillis));
        if (last2Versions.isEmpty())
            throw new VersionNotFoundException(String.valueOf(versionSaveDateMillis));
        Version currentVersion = last2Versions.get(0);
        FileContentDTO oldContent = null;
        if (last2Versions.size() != 1) {
            Version previousVersion = last2Versions.get(1);
            oldContent = new FileContentDTO(getLines(previousVersion));
        }
        return new DiffData(
                currentVersion.getDifferences(),
                new FileContentDTO(getLines(currentVersion)),
                oldContent
        );
    }

    private List<String> getLines(Version version) {
        byte[] bytes = version.getFileContent();
        try {
            return tikaService.extractLines(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            LOGGER.error("Input/output exception occurred during extraction of lines");
            throw new RuntimeException(e);
        }
    }

    @Override
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
    public boolean existsByVersionString(ObjectId fileId, String versionString) {
        return this.anyVersionMatch(fileId, version -> versionString.equals(version.getVersionString()));
    }

    private boolean anyVersionMatch(ObjectId fileId, Predicate<Version> versionPredicate) {
        return versionRepository.findByFile_Id(fileId).stream().anyMatch(versionPredicate);
    }

    private FileMetadata getFileMetadata(ObjectId fileId) throws FileNotFoundException {
        return fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId.toString()));
    }

}
