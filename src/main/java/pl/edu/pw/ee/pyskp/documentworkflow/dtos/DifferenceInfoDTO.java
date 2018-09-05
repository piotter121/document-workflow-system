package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

/**
 * Created by p.pysk on 09.01.2017.
 */
@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class DifferenceInfoDTO {
    public static DifferenceInfoDTO fromDifference(Difference difference) {
        return new DifferenceInfoDTO(
                difference.getPreviousSectionStart(), difference.getPreviousSectionSize(),
                difference.getNewSectionStart(), difference.getNewSectionSize(),
                difference.getDifferenceType().name()
        );
    }

    private Long previousSectionStart, previousSectionSize,
            newSectionStart, newSectionSize;
    private String differenceType;
}
