package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;

import java.sql.Timestamp;

@Value
public class ProjectSummaryDTO {
    @NonNull
    String id;

    @NonNull
    String name;

    @NonNull
    Timestamp creationDate;

    @NonNull
    Integer numberOfParticipants, numberOfTasks, numberOfFiles;

    FileSummaryDTO lastModifiedFile;
}
