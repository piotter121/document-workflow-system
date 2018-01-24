package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import com.datastax.driver.core.DataType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.mapping.CassandraType;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Data
@Table("user_project")
@EqualsAndHashCode(of = {"userLogin", "projectId"})
@NoArgsConstructor
public class UserProject {
    @PrimaryKeyColumn(name = "user_login", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userLogin;

    @CassandraType(type = DataType.Name.UUID)
    @PrimaryKeyColumn(name = "project_id", ordinal = 1)
    private UUID projectId;

    private String name;

    private Date creationDate;

    private FileSummary lastModifiedFile;

    private long numberOfParticipants = 1;

    private long numberOfTasks = 0;

    private long numberOfFiles = 0;

    public UserProject(User user, Project project) {
        userLogin = user.getLogin();
        projectId = project.getId();
        name = project.getName();
        creationDate = project.getCreationDate();
    }

    @Transient
    public void incrementNumberOfFiles() {
        numberOfFiles++;
    }
}
