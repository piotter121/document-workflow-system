package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;
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
    OffsetDateTime creationDate;

    OffsetDateTime lastModified;

    List<TaskSummaryDTO> tasks;
}
