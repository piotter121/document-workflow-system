package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Table("project")
public class Project {
    @PrimaryKey("id")
    @CassandraType(type = DataType.Name.UUID)
    private UUID id = UUID.randomUUID();

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("creation_date")
    private Date creationDate;

    @Column("administrator")
    private UserSummary administrator;
}