package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.mapping.UserDefinedType;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@UserDefinedType
@EqualsAndHashCode
public class Difference {
    private long previousSectionStart;

    private long previousSectionSize;

    private long newSectionStart;

    private long newSectionSize;

    private DifferenceType differenceType;
}