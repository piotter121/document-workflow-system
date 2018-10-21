package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = {"fileId", "saveDate"})
@Table("version")
public class Version {
    @SerializedName("file_id")
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "file_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID fileId;

    @SerializedName("save_date")
    @PrimaryKeyColumn(name = "save_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date saveDate;

    @SerializedName("version_string")
    @Column("version_string")
    private String versionString;

    @Column("message")
    private String message;

    @Column("author")
    private UserSummary author;

    @Column("file_content")
    @CassandraType(type = DataType.Name.BLOB)
    private ByteBuffer fileContent;

    @SerializedName("parsed_file_content")
    @Column("parsed_file_content")
    @CassandraType(type = DataType.Name.TEXT)
    private String parsedFileContent;

    @SerializedName("check_sum")
    @Column("check_sum")
    private String checkSum;

    @Column("differences")
    private List<Difference> differences;
}
