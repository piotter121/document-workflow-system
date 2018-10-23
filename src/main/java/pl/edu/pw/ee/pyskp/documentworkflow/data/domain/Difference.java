package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;

/**
 * Created by piotr on 11.12.16.
 */
@Data
public class Difference {
    private int previousSectionStart, previousSectionSize;

    private int newSectionStart, newSectionSize;

    private DifferenceType differenceType;
}