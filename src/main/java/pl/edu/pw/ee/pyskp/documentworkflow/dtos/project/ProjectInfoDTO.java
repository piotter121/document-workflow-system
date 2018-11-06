package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by piotr on 29.12.16.
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectInfoDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String id;

    @NonNull
    String name;

    String description;

    @NonNull
    UserInfoDTO administrator;

    @NonNull
    Timestamp creationDate;

    Timestamp lastModified;

    @ToString.Exclude
    List<TaskSummaryDTO> tasks;
}
