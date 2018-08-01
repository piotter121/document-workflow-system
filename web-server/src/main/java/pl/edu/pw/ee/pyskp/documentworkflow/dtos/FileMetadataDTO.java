package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by piotr on 31.12.16.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class FileMetadataDTO {
    private String id;
    private String name = "";
    private String description = "";
    private String contentType = "";
    private boolean confirmed = false;
    private boolean markedToConfirm = false;
    private Date creationDate;
    private Date modificationDate;
    private List<VersionInfoDTO> versions;
    private String extension;
    private VersionSummaryDTO latestVersion;
    private long numberOfVersions;

    public FileMetadataDTO(FileMetadata fileMetadata, List<Version> versions) {
        setId(fileMetadata.getFileId().toString());
        setName(fileMetadata.getName());
        setDescription(fileMetadata.getDescription());
        setContentType(fileMetadata.getContentType().name());
        setConfirmed(fileMetadata.isConfirmed());
        setModificationDate(fileMetadata.getLatestVersion().getSaveDate());
        setCreationDate(fileMetadata.getCreationDate());
        setMarkedToConfirm(fileMetadata.isMarkedToConfirm());
        setVersions(versions
                .stream().map(VersionInfoDTO::new).collect(Collectors.toList()));
        extension = fileMetadata.getContentType().getExtension();
        latestVersion = new VersionSummaryDTO(fileMetadata.getLatestVersion());
        numberOfVersions = fileMetadata.getNumberOfVersions();
    }

    FileMetadataDTO(FileMetadata fileMetadata) {
        this(fileMetadata, Collections.emptyList());
    }

    public List<VersionInfoDTO> getVersionSortedBySaveDateDESC() {
        return versions.stream()
                .sorted(comparing(VersionInfoDTO::getSaveDate).reversed())
                .collect(toList());
    }
}
