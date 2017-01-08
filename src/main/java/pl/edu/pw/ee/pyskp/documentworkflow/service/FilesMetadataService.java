package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.exception.UnknownContentType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by p.pysk on 04.01.2017.
 */
public interface FilesMetadataService {

    FileMetadata createNewFileFromForm(NewFileForm formData, Task task) throws UnknownContentType;

    Optional<FileMetadata> getOneById(long id);

    static FileMetadataDTO mapToFileMetadataDTO(FileMetadata fileMetadata) {
        FileMetadataDTO dto = new FileMetadataDTO();
        dto.setId(fileMetadata.getId());
        dto.setName(fileMetadata.getName());
        dto.setDescription(fileMetadata.getDescription());
        dto.setContentType(fileMetadata.getContentType().name());
        dto.setConfirmed(fileMetadata.isConfirmed());
        dto.setModificationDate(fileMetadata.getModificationDate());
        dto.setCreationDate(fileMetadata.getCreationDate());
        dto.setMarkedToConfirm(fileMetadata.isMarkedToConfirm());
        dto.setVersions(VersionService.mapAllToVersionInfoDTO(fileMetadata.getVersions()));
        dto.setLatestVersion(VersionService.mapToVersionInfoDTO(fileMetadata.getLatestVersion()));
        return dto;
    }

    static List<FileMetadataDTO> mapAllToFileMetadataDTO(Collection<FileMetadata> collection) {
        return collection != null
                ? collection.stream()
                .map(FilesMetadataService::mapToFileMetadataDTO)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
