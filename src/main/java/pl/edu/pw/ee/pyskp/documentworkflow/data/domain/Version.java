package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table("version")
public class Version {
    @EqualsAndHashCode.Include
    @SerializedName(SerializedFields.FILE_ID_FIELD)
    @CassandraType(type = DataType.Name.BIGINT)
    @PrimaryKeyColumn(name = "file_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long fileId;

    @EqualsAndHashCode.Include
    @SerializedName("save_date")
    @CassandraType(type = DataType.Name.TIMESTAMP)
    @PrimaryKeyColumn(name = "save_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date saveDate;

    @SerializedName("version_string")
    @Column("version_string")
    @CassandraType(type = DataType.Name.TEXT)
    private String versionString;

    @Column("message")
    @CassandraType(type = DataType.Name.TEXT)
    private String message;

    @Column("author")
    @CassandraType(type = DataType.Name.UDT, userTypeName = "user_summary")
    private UserSummary author;

    @ToString.Exclude
    @Column("file_content")
    @CassandraType(type = DataType.Name.BLOB)
    private ByteBuffer fileContent;

    @ToString.Exclude
    @SerializedName(SerializedFields.PARSED_FILE_CONTENT_FIELD)
    @Column("parsed_file_content")
    @CassandraType(type = DataType.Name.TEXT)
    private String parsedFileContent;

    @SerializedName("check_sum")
    @Column("check_sum")
    @CassandraType(type = DataType.Name.TEXT)
    private String checkSum;

    @ToString.Exclude
    @Column("differences")
    @CassandraType(type = DataType.Name.LIST, typeArguments = DataType.Name.UDT, userTypeName = "difference")
    private List<Difference> differences;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class SerializedFields {
        public static final String
                PARSED_FILE_CONTENT_FIELD = "parsed_file_content",
                FILE_ID_FIELD = "file_id";
    }
}
