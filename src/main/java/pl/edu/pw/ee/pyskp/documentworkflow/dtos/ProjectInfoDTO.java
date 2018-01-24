package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 29.12.16.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProjectInfoDTO {
    private String id;
    private String name = "";
    private String description = "";
    private UserInfoDTO administrator;
    private Date creationDate;
    private Date lastModified;
    private List<TaskSummaryDTO> tasks = new ArrayList<>();

    public ProjectInfoDTO(Project project, List<Task> projectTasks) {
        id = project.getId().toString();
        name = project.getName();
        description = project.getDescription();
        administrator = new UserInfoDTO(project.getAdministrator());
        creationDate = project.getCreationDate();
        tasks = projectTasks.stream().map(TaskSummaryDTO::new).collect(Collectors.toList());
        tasks.stream().map(task -> task.getLastModifiedFile().getSaveDate())
                .max(Date::compareTo)
                .ifPresent(this::setLastModified);
    }
}
