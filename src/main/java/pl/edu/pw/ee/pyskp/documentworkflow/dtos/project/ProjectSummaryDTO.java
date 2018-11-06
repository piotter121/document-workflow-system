package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectSummaryDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String id;

    @NonNull
    String name;

    @NonNull
    Timestamp creationDate;

    @NonNull
    Integer numberOfParticipants, numberOfTasks, numberOfFiles;

    @ToString.Exclude
    FileSummaryDTO lastModifiedFile;
}
