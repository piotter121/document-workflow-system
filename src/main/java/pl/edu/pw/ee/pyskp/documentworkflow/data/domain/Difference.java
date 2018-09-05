package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by piotr on 11.12.16.
 */
@Data
public class Difference {
    private Long previousSectionStart, previousSectionSize;

    private Long newSectionStart, newSectionSize;

    private DifferenceType differenceType;
}