package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.validation.annotation.Validated;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;

import javax.validation.Valid;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * Created by piotr on 06.01.17.
 */
@Validated
public interface VersionService {
    void createInitVersionOfFile(NewFileForm form, FileMetadata fileMetadata);

    InputStream getVersionFileContent(long fileId, OffsetDateTime saveDate) throws VersionNotFoundException;

    OffsetDateTime addNewVersionOfFile(@Valid NewVersionForm form) throws ResourceNotFoundException;

    DiffData buildDiffData(long fileId, OffsetDateTime versionSaveDate) throws VersionNotFoundException;

    VersionInfoDTO getVersionInfo(long fileId, OffsetDateTime versionSaveDate) throws VersionNotFoundException;

    boolean existsByVersionString(long fileId, String versionString);

    void deleteProjectFilesVersions(long projectId);

    void deleteTaskFilesVersions(long taskId);

    int getNumberOfVersions(FileMetadata fileMetadata);
}
