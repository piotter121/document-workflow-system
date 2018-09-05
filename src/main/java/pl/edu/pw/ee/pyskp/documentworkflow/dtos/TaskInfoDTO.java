package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 31.12.16.
 */
@Data
public class TaskInfoDTO {
    private final String id;
    private final String name;
    private final String description;
    private final String projectId, projectName;
    private final UserInfoDTO projectAdministrator;
    private final UserInfoDTO administrator;
    private final Date creationDate;
    private final Date modificationDate;
    private final FileSummaryDTO lastModifiedFile;
    private final List<UserInfoDTO> participants;
    private final List<FileMetadataDTO> filesInfo;

    public static TaskInfoDTO fromTaskAndFiles(Task task, List<FileMetadata> files) {
        Project project = task.getProject();
        return new TaskInfoDTO(
                task.getId().toString(),
                task.getName(),
                task.getDescription(),
                project.getId().toString(),
                project.getName(),
                UserInfoDTO.fromUser(project.getAdministrator()),
                UserInfoDTO.fromUser(task.getAdministrator()),
                task.getCreationDate(),
                task.getLastModifiedFile().getLatestVersion().getSaveDate(),
                FileSummaryDTO.fromFileMetadata(task.getLastModifiedFile()),
                task.getParticipants().stream()
                        .map(UserInfoDTO::fromUser)
                        .collect(Collectors.toList()),
                files.stream()
                    .map(FileMetadataDTO::fromFileMetadata)
                    .collect(Collectors.toList())
        );
    }
}
