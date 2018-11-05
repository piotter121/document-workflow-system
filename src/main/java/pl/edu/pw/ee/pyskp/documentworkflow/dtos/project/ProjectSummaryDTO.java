package pl.edu.pw.ee.pyskp.documentworkflow.dtos.project;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Project;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileSummaryDTO;

import java.util.Date;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectSummaryDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String id;

    @NonNull
    String name;

    @NonNull
    Date creationDate;

    long numberOfParticipants, numberOfTasks, numberOfFiles;

    FileSummaryDTO lastModifiedFile;

    public static ProjectSummaryDTO fromProject(Project project) {
        FileMetadata lastModifiedFile = project.getLastModifiedFile();
        FileSummaryDTO lastModifiedFileDTO = null;
        if (lastModifiedFile != null) {
            Version lastModifiedFileLatestVersion = lastModifiedFile.getLatestVersion();
            lastModifiedFileDTO = new FileSummaryDTO(
                    lastModifiedFile.getName(),
                    lastModifiedFileLatestVersion.getSaveDate(),
                    lastModifiedFileLatestVersion.getAuthor().getFullName(),
                    lastModifiedFile.getTask().getName()
            );
        }
        return new ProjectSummaryDTO(
                project.getId().toString(),
                project.getName(),
                project.getCreationDate(),
                project.getNumberOfParticipants(),
                project.getNumberOfTasks(),
                project.getNumberOfFiles(),
                lastModifiedFileDTO
        );
    }
}
