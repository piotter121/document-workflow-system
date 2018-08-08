package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;

@NoArgsConstructor
@Data
@UserDefinedType("file_summary")
public class FileSummary {
    @Column("name")
    private String name;

    @Column("modification_date")
    private Date modificationDate;

    @Column("modification_author")
    private UserSummary modificationAuthor;

    @Column("task_name")
    private String taskName;

    public FileSummary(FileMetadata fileMetadata) {
        name = fileMetadata.getName();
        modificationDate = fileMetadata.getLatestVersion().getSaveDate();
        modificationAuthor = fileMetadata.getLatestVersion().getModificationAuthor();
        taskName = fileMetadata.getTaskName();
    }
}
