package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = {"fileId", "saveDate"})
@Table("version")
public class Version {
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "file_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID fileId;

    @PrimaryKeyColumn(name = "save_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date saveDate;

    @Column("version_string")
    private String versionString;

    @Column("message")
    private String message;

    @Column("author")
    private UserSummary author;

    @Column("file_content")
    @CassandraType(type = DataType.Name.BLOB)
    private ByteBuffer fileContent;

    @Column("check_sum")
    private String checkSum;

    @Column("differences")
    private Set<Difference> differences;
}
