package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.*;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = {"projectId", "taskId"})
@Table("task")
public class Task {
    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "project_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID projectId;

    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "task_id", ordinal = 1)
    private UUID taskId = UUID.randomUUID();

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("creation_date")
    private Date creationDate = new Date();

    @Column("administrator")
    private UserSummary administrator;

    @Column("participants")
    private Set<UserSummary> participants = new HashSet<>();

    @Column("last_modified_file")
    private FileSummary lastModifiedFile;

    @Column("number_of_files")
    private long numberOfFiles = 0;

    @Transient
    public Date getModificationDate() {
        return lastModifiedFile == null ? creationDate : lastModifiedFile.getModificationDate();
    }

    @Transient
    public void incrementNumberOfFiles() {
        numberOfFiles++;
    }

    public Set<UserSummary> getParticipants() {
        return participants == null ? Collections.emptySet() : participants;
    }
}
