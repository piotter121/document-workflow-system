package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.springframework.validation.annotation.Validated;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
@Validated
public interface VersionService {
    Version createUnmanagedInitVersionOfFile(NewFileForm form);

    InputStream getVersionFileContent(UUID fileId, Date saveDate) throws VersionNotFoundException;

    long addNewVersionOfFile(@Valid NewVersionForm form) throws ResourceNotFoundException;

    DiffData buildDiffData(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException;

    VersionInfoDTO getVersionInfo(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException;

    boolean existsByVersionString(UUID fileId, String versionString);
}
