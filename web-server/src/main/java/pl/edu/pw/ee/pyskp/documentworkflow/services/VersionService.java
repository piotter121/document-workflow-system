package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionService {
    Version createUnmanagedInitVersionOfFile(NewFileForm form) throws IOException;

    long addNewVersionOfFile(NewVersionForm form) throws IOException;

    DiffData buildDiffData(UUID fileId, long versionSaveDateMillis);

    VersionInfoDTO getVersionInfo(UUID fileId, long versionSaveDateMillis);
}
