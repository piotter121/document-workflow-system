package pl.edu.pw.ee.pyskp.documentworkflow.dtos.task;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskInfoDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String id;

    @NonNull
    String name;

    String description;

    @NonNull
    String projectId, projectName;

    @NonNull
    @ToString.Exclude
    UserInfoDTO projectAdministrator, administrator;

    @NonNull
    Timestamp creationDate, modificationDate;

    @ToString.Exclude
    List<UserInfoDTO> participants;

    @ToString.Exclude
    FileSummaryDTO lastModifiedFile;

    @ToString.Exclude
    List<FileMetadataDTO> filesInfo;
}
