package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
@Data
public class TaskSummaryDTO {
    private final String id;
    private final String name;
    private final Date creationDate;
    private FileSummaryDTO lastModifiedFile;
    private final Integer numberOfFiles, numberOfParticipants;

    public static TaskSummaryDTO fromTask(Task task) {
        TaskSummaryDTO dto = new TaskSummaryDTO(
                task.getId().toString(),
                task.getName(),
                task.getCreationDate(),
                task.getNumberOfFiles(),
                task.getParticipants().size()
        );
        if (task.getLastModifiedFile() != null) {
            dto.setLastModifiedFile(FileSummaryDTO.fromFileMetadata(task.getLastModifiedFile()));
        }
        return dto;
    }
}
