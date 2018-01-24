package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Table
@Data
@EqualsAndHashCode(of = "id")
public class Project {
    @PrimaryKey
    @CassandraType(type = DataType.Name.UUID)
    private UUID id = UUID.randomUUID();

    @Length(min = 5, max = 40)
    private String name;

    private String description;

    private Date creationDate;

    private UserSummary administrator;
}