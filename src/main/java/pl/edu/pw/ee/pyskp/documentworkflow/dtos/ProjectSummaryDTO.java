package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;

@Data
public class ProjectSummaryDTO {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final Date creationDate;

    @NonNull
    private final Integer numberOfParticipants;

    @NonNull
    private final Integer numberOfTasks;

    @NonNull
    private final Integer numberOfFiles;

    private FileSummaryDTO lastModifiedFile;

    public static ProjectSummaryDTO fromProject(Project project) {
        ProjectSummaryDTO dto = new ProjectSummaryDTO(
                project.getId().toString(),
                project.getName(),
                project.getCreationDate(),
                project.getNumberOfParticipants(),
                project.getNumberOfTasks(),
                project.getNumberOfFiles()
        );
        FileMetadata lastModifiedFile = project.getLastModifiedFile();
        if (lastModifiedFile != null) {
            Version lastModifiedFileLatestVersion = lastModifiedFile.getLatestVersion();
            dto.setLastModifiedFile(new FileSummaryDTO(
                    lastModifiedFile.getName(),
                    lastModifiedFileLatestVersion.getSaveDate(),
                    lastModifiedFileLatestVersion.getAuthor().getFullName(),
                    lastModifiedFile.getTask().getName()
            ));
        }
        return dto;
    }
}
