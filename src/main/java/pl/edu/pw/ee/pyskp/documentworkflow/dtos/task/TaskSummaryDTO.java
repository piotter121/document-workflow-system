package pl.edu.pw.ee.pyskp.documentworkflow.dtos.task;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;

import java.util.Date;

@Value
public class TaskSummaryDTO {
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private Date creationDate;

    private FileSummaryDTO lastModifiedFile;

    private long numberOfFiles, numberOfParticipants;

    public static TaskSummaryDTO fromTask(Task task) {
        FileSummaryDTO lastModifiedFile = null;
        if (task.getLastModifiedFile() != null) {
            lastModifiedFile = FileSummaryDTO.fromFileMetadata(task.getLastModifiedFile());
        }
        return new TaskSummaryDTO(
                task.getId().toString(),
                task.getName(),
                task.getCreationDate(),
                lastModifiedFile,
                task.getNumberOfFiles(),
                task.getParticipants().size()
        );
    }
}
