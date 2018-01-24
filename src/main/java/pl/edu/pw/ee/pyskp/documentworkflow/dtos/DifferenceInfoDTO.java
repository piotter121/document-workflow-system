package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.DifferenceType;

/**
 * Created by p.pysk on 09.01.2017.
 */
@Data
@NoArgsConstructor
public class DifferenceInfoDTO {
    private long previousSectionStart;
    private long previousSectionSize;
    private long newSectionStart;
    private long newSectionSize;
    private DifferenceType differenceType;

    public DifferenceInfoDTO(Difference difference) {
        setDifferenceType(difference.getDifferenceType());
        setNewSectionSize(difference.getNewSectionSize());
        setNewSectionStart(difference.getNewSectionStart());
        setPreviousSectionSize(difference.getPreviousSectionSize());
        setPreviousSectionStart(difference.getPreviousSectionStart());
    }

}
