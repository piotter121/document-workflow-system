package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.task.TaskSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.sql.Timestamp;
import java.util.List;

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
    Timestamp creationDate;

    Timestamp lastModified;

    List<TaskSummaryDTO> tasks;
}
