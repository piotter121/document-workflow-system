package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileMetadataDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String id;

    @NonNull
    String name;

    String description;

    @NonNull
    String contentType, extension;

    boolean confirmed, markedToConfirm;

    @NonNull
    Timestamp creationDate, modificationDate;

    @NonNull
    VersionSummaryDTO latestVersion;

    long numberOfVersions;

    @ToString.Exclude
    List<VersionInfoDTO> versions;
}
