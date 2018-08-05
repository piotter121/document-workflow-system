package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.TaskNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.VersionNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionService {
    Version createUnmanagedInitVersionOfFile(NewFileForm form);

    InputStream getVersionFileContent(UUID fileId, Date saveDate) throws VersionNotFoundException;

    long addNewVersionOfFile(NewVersionForm form) throws ResourceNotFoundException;

    DiffData buildDiffData(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException;

    VersionInfoDTO getVersionInfo(UUID fileId, long versionSaveDateMillis) throws VersionNotFoundException;
}
