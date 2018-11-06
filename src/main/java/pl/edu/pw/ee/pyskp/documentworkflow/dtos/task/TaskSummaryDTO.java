package pl.edu.pw.ee.pyskp.documentworkflow.dtos.task;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskSummaryDTO {
    @NonNull
    @EqualsAndHashCode.Include
    private String id;

    @NonNull
    private String name;

    @NonNull
    private Timestamp creationDate;

    private FileSummaryDTO lastModifiedFile;

    private long numberOfFiles, numberOfParticipants;
}
