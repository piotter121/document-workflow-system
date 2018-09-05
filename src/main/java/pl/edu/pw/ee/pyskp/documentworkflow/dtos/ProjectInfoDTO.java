package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
@Data
public class ProjectInfoDTO {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    private final String description;

    @NonNull
    private final UserInfoDTO administrator;

    @NonNull
    private final Date creationDate;

    private final Date lastModified;

    private final List<TaskSummaryDTO> tasks;

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
