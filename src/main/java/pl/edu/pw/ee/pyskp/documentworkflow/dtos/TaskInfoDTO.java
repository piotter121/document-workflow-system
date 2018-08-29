package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Task;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 31.12.16.
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class TaskInfoDTO {
    private String id;
    private String name;
    private String description;
    private String projectId, projectName;
    private UserInfoDTO projectAdministrator;
    private UserInfoDTO administrator;
    private Date creationDate;
    private Date modificationDate;
    private List<UserInfoDTO> participants;
    private FileSummaryDTO lastModifiedFile;
    private List<FileMetadataDTO> filesInfo;

    public TaskInfoDTO(Task task, List<FileMetadata> files) {
        setId(task.getTaskId().toString());
        setName(task.getName());
        setDescription(task.getDescription());
        setAdministrator(new UserInfoDTO(task.getAdministrator()));
        setCreationDate(task.getCreationDate());
        setModificationDate(task.getModificationDate());
        setProjectId(task.getProjectId().toString());
        setParticipants(task.getParticipants()
                .stream().map(UserInfoDTO::new).collect(Collectors.toList()));
        if (task.getLastModifiedFile() != null)
            lastModifiedFile = new FileSummaryDTO(task.getLastModifiedFile());
        filesInfo = files.stream()
                .sorted(Comparator.comparing((FileMetadata file) -> file.getLatestVersion().getSaveDate()).reversed())
                .map(FileMetadataDTO::new)
                .collect(Collectors.toList());
    }
}
