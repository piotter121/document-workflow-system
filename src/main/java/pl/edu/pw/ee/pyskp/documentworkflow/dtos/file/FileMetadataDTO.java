package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionInfoDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.version.VersionSummaryDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    Date creationDate, modificationDate;

    @NonNull
    VersionSummaryDTO latestVersion;

    long numberOfVersions;

    List<VersionInfoDTO> versions;

    public static FileMetadataDTO fromFileMetadataAndVersions(FileMetadata fileMetadata, Collection<Version> versions) {
        List<VersionInfoDTO> versionDTOs = versions.stream()
                .map(VersionInfoDTO::fromVersion)
                .collect(Collectors.toList());
        return new FileMetadataDTO(
                fileMetadata.getId().toString(),
                fileMetadata.getName(),
                fileMetadata.getDescription(),
                fileMetadata.getContentType().name(),
                fileMetadata.getContentType().getExtension(),
                fileMetadata.getConfirmed(),
                fileMetadata.getMarkedToConfirm(),
                fileMetadata.getCreationDate(),
                fileMetadata.getLatestVersion() != null ?
                        fileMetadata.getLatestVersion().getSaveDate() : fileMetadata.getCreationDate(),
                VersionSummaryDTO.fromVersion(fileMetadata.getLatestVersion()),
                fileMetadata.getNumberOfVersions(),
                versionDTOs
        );
    }

    public static FileMetadataDTO fromFileMetadata(FileMetadata fileMetadata) {
        return fromFileMetadataAndVersions(fileMetadata, Collections.emptyList());
    }
}
