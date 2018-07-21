package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.UserProject;

import java.util.Date;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class ProjectSummaryDTO {
    private String id;
    private String name;
    private Date creationDate;
    private FileSummaryDTO lastModifiedFile;
    private long numberOfParticipants;
    private long numberOfTasks;
    private long numberOfFiles;

    public ProjectSummaryDTO(UserProject userProject) {
        id = userProject.getProjectId().toString();
        name = userProject.getName();
        creationDate = userProject.getCreationDate();
        if (userProject.getLastModifiedFile() != null)
            lastModifiedFile = new FileSummaryDTO(userProject.getLastModifiedFile());
        numberOfParticipants = userProject.getNumberOfParticipants();
        numberOfTasks = userProject.getNumberOfTasks();
        numberOfFiles = userProject.getNumberOfFiles();
    }
}
