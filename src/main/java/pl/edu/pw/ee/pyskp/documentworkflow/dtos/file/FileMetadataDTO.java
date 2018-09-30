package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
@Value
public class FileMetadataDTO {
    @NonNull
    String id, name;

    String description;

    @NonNull
    String contentType, extension;

    boolean confirmed, markedToConfirm;

    @NonNull
    Timestamp creationDate, modificationDate;

    @NonNull
    VersionSummaryDTO latestVersion;

    int numberOfVersions;

    List<VersionInfoDTO> versions;
}
