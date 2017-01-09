package pl.edu.pw.ee.pyskp.documentworkflow.service;

import org.apache.tika.exception.TikaException;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.VersionInfoDTO;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionService {
    Version createUnmanagedInitVersionOfFile(NewFileForm form) throws IOException, TikaException;

    static VersionInfoDTO mapToVersionInfoDTO(Version version) {
        VersionInfoDTO dto = new VersionInfoDTO();
        dto.setId(version.getId());
        dto.setAuthor(UserService.mapToUserInfoDTO(version.getAuthor()));
        dto.setSaveDate(version.getSaveDate());
        dto.setVersionString(version.getVersionString());
        dto.setMessage(version.getMessage());
        dto.setDifferences(DifferenceService.mapAllToDifferenceInfoDTO(version.getDifferences()));
        return dto;
    }

    static List<VersionInfoDTO> mapAllToVersionInfoDTO(Collection<Version> versions) {
        return versions != null
                ? versions.stream()
                .map(VersionService::mapToVersionInfoDTO).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
