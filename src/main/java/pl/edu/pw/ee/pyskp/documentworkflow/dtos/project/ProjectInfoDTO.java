package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
@Value
public class ProjectInfoDTO {
    @NonNull
    String id;

    @NonNull
    String name;

    String description;

    @NonNull
    UserInfoDTO administrator;

    @NonNull
    Date creationDate;

    Date lastModified;

    List<TaskSummaryDTO> tasks;

    public static ProjectInfoDTO fromProjectAndTasks(Project project, Collection<Task> tasks) {
        Date lastModified = null;
        FileMetadata lastModifiedFile = project.getLastModifiedFile();
        if (lastModifiedFile != null) {
            lastModified = lastModifiedFile.getLatestVersion().getSaveDate();
        }
        List<TaskSummaryDTO> taskSummaryDTOS = tasks.stream()
                .map(TaskSummaryDTO::fromTask)
                .collect(Collectors.toList());
        return new ProjectInfoDTO(
                project.getId().toString(),
                project.getName(),
                project.getDescription(),
                UserInfoDTO.fromUser(project.getAdministrator()),
                project.getCreationDate(),
                lastModified,
                taskSummaryDTOS
        );
    }
}
