package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.bson.types.ObjectId;
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

/**
 * Created by piotr on 06.01.17.
 */
@Validated
public interface VersionService {
    Version createInitVersionOfFile(NewFileForm form);

    InputStream getVersionFileContent(ObjectId fileId, Date saveDate) throws VersionNotFoundException;

    long addNewVersionOfFile(@Valid NewVersionForm form) throws ResourceNotFoundException;

    DiffData buildDiffData(ObjectId fileId, long versionSaveDateMillis) throws VersionNotFoundException;

    VersionInfoDTO getVersionInfo(ObjectId fileId, long versionSaveDateMillis) throws VersionNotFoundException;

    boolean existsByVersionString(ObjectId fileId, String versionString);
}
