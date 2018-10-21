package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@UserDefinedType("difference")
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