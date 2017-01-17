package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.DifferenceInfoDTO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */
public interface DifferenceService {
    List<Difference> createDifferencesForNewFile(InputStream inputStream) throws IOException;

    List<Difference> getDifferencesBetweenTwoFiles(
            InputStream inputStream, InputStream anotherInputStream) throws IOException;

    static DifferenceInfoDTO mapToDifferenceInfoDTO(Difference difference) {
        DifferenceInfoDTO dto = new DifferenceInfoDTO();
        dto.setDifferenceType(difference.getDifferenceType());
        dto.setNewSectionSize(difference.getNewSectionSize());
        dto.setNewSectionStart(difference.getNewSectionStart());
        dto.setPreviousSectionSize(difference.getPreviousSectionSize());
        dto.setPreviousSectionStart(difference.getPreviousSectionStart());
        return dto;
    }

    static List<DifferenceInfoDTO> mapAllToDifferenceInfoDTO(Collection<Difference> differences) {
        return differences != null
                ? differences.stream()
                .map(DifferenceService::mapToDifferenceInfoDTO)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
