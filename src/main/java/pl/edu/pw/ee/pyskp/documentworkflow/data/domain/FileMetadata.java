package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

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
    private UUID fileId;

    @Column("name")
    private String name;

    @Column("task_name")
    private String taskName;

    @Column("description")
    private String description;

    @Column("content_type")
    private ContentType contentType;

    @Column("confirmed")
    private boolean confirmed;

    @Column("marked_to_confirm")
    private boolean markedToConfirm;

    @Column("creation_date")
    private Date creationDate;

    @Column("number_of_versions")
    private long numberOfVersions;

    @Column("latest_version")
    private VersionSummary latestVersion;

    @Transient
    public Date getModificationDate() {
        return latestVersion == null ? creationDate : latestVersion.getSaveDate();
    }
}
