package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.FileMetadataDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by p.pysk on 04.01.2017.
 */
public interface FilesMetadataService {
    static FileMetadataDTO mapToFileMetadataDTO(FileMetadata fileMetadata) {
        FileMetadataDTO dto = new FileMetadataDTO();
        dto.setId(fileMetadata.getId());
        dto.setName(fileMetadata.getName());
        dto.setDescription(fileMetadata.getDescription());
        dto.setContentType(fileMetadata.getContentType().name());
        dto.setConfirmed(fileMetadata.isConfirmed());
        dto.setMarkedToConfirm(fileMetadata.isMarkedToConfirm());
        fileMetadata.getLatestVersion().ifPresent(
                version -> dto.setModificationDate(version.getSaveDate()));
        return dto;
    }

    static List<FileMetadataDTO> mapAllToFileMetadataDTO(Collection<FileMetadata> collection) {
        return collection != null
                ? collection.stream().map(FilesMetadataService::mapToFileMetadataDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
