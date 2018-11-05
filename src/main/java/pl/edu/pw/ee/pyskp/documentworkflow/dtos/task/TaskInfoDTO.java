package pl.edu.pw.ee.pyskp.documentworkflow.dtos.task;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileMetadataDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @ToString.Exclude
    String description;

    @NonNull
    String projectId, projectName;

    @NonNull
    UserInfoDTO projectAdministrator, administrator;

    @NonNull
    Date creationDate, modificationDate;

    @ToString.Exclude
    List<UserInfoDTO> participants;

    FileSummaryDTO lastModifiedFile;

    @ToString.Exclude
    List<FileMetadataDTO> filesInfo;

    public static TaskInfoDTO fromTaskAndFiles(Task task, List<FileMetadata> files) {
        Project project = task.getProject();
        Date modificationDate;
        FileSummaryDTO lastModifiedFile = null;
        if (task.getLastModifiedFile() != null) {
            modificationDate = task.getLastModifiedFile().getLatestVersion().getSaveDate();
            lastModifiedFile = FileSummaryDTO.fromFileMetadata(task.getLastModifiedFile());
        } else {
            modificationDate = task.getCreationDate();
        }
        return new TaskInfoDTO(
                task.getId().toString(),
                task.getName(),
                task.getDescription(),
                project.getId().toString(),
                project.getName(),
                UserInfoDTO.fromUser(project.getAdministrator()),
                UserInfoDTO.fromUser(task.getAdministrator()),
                task.getCreationDate(),
                modificationDate,
                task.getParticipants().stream()
                        .map(UserInfoDTO::fromUser)
                        .collect(Collectors.toList()),
                lastModifiedFile,
                files.stream()
                        .map(FileMetadataDTO::fromFileMetadata)
                        .collect(Collectors.toList())

        );
    }
}
