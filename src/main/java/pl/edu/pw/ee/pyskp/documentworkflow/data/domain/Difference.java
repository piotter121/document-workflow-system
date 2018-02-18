package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode
@UserDefinedType
public class Difference {
    @Column("previous_section_start")
    private long previousSectionStart;

    @Column("previous_section_size")
    private long previousSectionSize;

    @Column("new_section_start")
    private long newSectionStart;

    @Column("new_section_size")
    private long newSectionSize;

    @Column("difference_type")
    private DifferenceType differenceType;
}