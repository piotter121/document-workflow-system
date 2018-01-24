package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@Table("file_metadata")
@EqualsAndHashCode(of = {"taskId", "fileId"})
public class FileMetadata {
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "task_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID taskId;

    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "file_id", ordinal = 1)
    private UUID fileId = UUID.randomUUID();

    private String name;

    private String taskName;

    private String description;

    private ContentType contentType;

    private boolean confirmed = false;

    private boolean markedToConfirm = false;

    private Date creationDate = new Date();

    private long numberOfVersions = 1L;

    private VersionSummary latestVersion;
}
