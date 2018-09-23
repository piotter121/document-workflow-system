package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.DifferenceType;

/**
 * Created by p.pysk on 09.01.2017.
 */
@Value
public class DifferenceInfoDTO {
    long previousSectionStart, previousSectionSize;

    long newSectionStart, newSectionSize;

    @NonNull
    DifferenceType differenceType;
}
