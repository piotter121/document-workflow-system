package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 31.12.16.
 */
@Data
public class FileMetadataDTO {
    public static FileMetadataDTO fromFileMetadataAndVersions(FileMetadata fileMetadata, Collection<Version> versions) {
        FileMetadataDTO dto = new FileMetadataDTO();
        dto.setId(fileMetadata.getId().toString());
        dto.setName(fileMetadata.getName());
        dto.setDescription(fileMetadata.getDescription());
        dto.setContentType(fileMetadata.getContentType().name());
        dto.setConfirmed(fileMetadata.getConfirmed());
        dto.setMarkedToConfirm(fileMetadata.getMarkedToConfirm());
        dto.setCreationDate(fileMetadata.getCreationDate());
        Version latestVersion = fileMetadata.getLatestVersion();
        if (latestVersion != null) {
            dto.setModificationDate(latestVersion.getSaveDate());
        }
        dto.setVersions(versions.stream()
                .map(VersionInfoDTO::fromVersion)
                .collect(Collectors.toList()));
        dto.setExtension(fileMetadata.getContentType().getExtension());
        dto.setLatestVersion(VersionSummaryDTO.fromVersion(fileMetadata.getLatestVersion()));
        dto.setNumberOfVersions(fileMetadata.getNumberOfVersions());
        return dto;
    }

    private String id;
    private String name;
    private String description;
    private String contentType;
    private Boolean confirmed;
    private Boolean markedToConfirm;
    private Date creationDate;
    private Date modificationDate;
    private List<VersionInfoDTO> versions;
    private String extension;
    private VersionSummaryDTO latestVersion;
    private Integer numberOfVersions;

    static FileMetadataDTO fromFileMetadata(FileMetadata fileMetadata) {
        return fromFileMetadataAndVersions(fileMetadata, Collections.emptyList());
    }
}
