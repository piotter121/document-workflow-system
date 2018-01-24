package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class TaskSummaryDTO {
    private String id;

    private String name;

    private Date creationDate;

    private FileSummaryDTO lastModifiedFile;

    private long numberOfFiles, numberOfParticipants;

    public TaskSummaryDTO(Task task) {
        id = task.getTaskId().toString();
        name = task.getName();
        creationDate = task.getCreationDate();
        lastModifiedFile = new FileSummaryDTO(task.getLastModifiedFile());
        numberOfFiles = task.getNumberOfFiles();
        numberOfParticipants = task.getParticipants().size();
    }
}
