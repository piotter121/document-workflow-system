package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.cassandra.core.Ordering;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Table
@Data
@EqualsAndHashCode(of = {"fileId", "saveDate"})
public class Version {
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "file_id", ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private UUID fileId;

    @PrimaryKeyColumn(name = "save_date", ordinal = 1,
            type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date saveDate;

    @Length(max = 20)
    private String versionString;

    @Length(max = 1024)
    private String message;

    private UserSummary author;

    private ByteBuffer fileContent;

    @Length(max = 65)
    private String checkSum;

    private Set<Difference> differences;
}
