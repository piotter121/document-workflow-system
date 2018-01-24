package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;

@Data
@UserDefinedType
@NoArgsConstructor
public class FileSummary {
    private String name;

    private Date modificationDate;

    private UserSummary modificationAuthor;

    private String taskName;

    public FileSummary(FileMetadata fileMetadata) {
        name = fileMetadata.getName();
        modificationDate = fileMetadata.getLatestVersion().getSaveDate();
        modificationAuthor = fileMetadata.getLatestVersion().getModificationAuthor();
        taskName = fileMetadata.getTaskName();
    }
}
