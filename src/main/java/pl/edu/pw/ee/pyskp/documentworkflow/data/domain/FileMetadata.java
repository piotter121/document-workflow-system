package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = {"taskId", "fileId"})
@Table("file_metadata")
public class FileMetadata {
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "task_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID taskId;

    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "file_id", ordinal = 1)
    private UUID fileId = UUID.randomUUID();

    private String name;

    @Column("task_name")
    private String taskName;

    private String description;

    @Column("content_type")
    private ContentType contentType;

    private boolean confirmed = false;

    @Column("marked_to_confirm")
    private boolean markedToConfirm = false;

    @Column("creation_date")
    private Date creationDate = new Date();

    @Column("number_of_versions")
    private long numberOfVersions = 1L;

    @NotNull
    @Column("latest_version")
    private VersionSummary latestVersion;
}
