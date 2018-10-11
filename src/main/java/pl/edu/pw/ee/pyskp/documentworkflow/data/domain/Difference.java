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
    private Integer previousSectionStart;

    @Column("previous_section_size")
    private Integer previousSectionSize;

    @Column("new_section_start")
    private Integer newSectionStart;

    @Column("new_section_size")
    private Integer newSectionSize;

    @Column("difference_type")
    private DifferenceType differenceType;
}