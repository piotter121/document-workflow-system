package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;

@Value
public class FileSummaryDTO {
    @NonNull
    String name;

    @NonNull
    Date saveDate;

    @NonNull
    String author;

    @NonNull
    String taskName;

    public static FileSummaryDTO fromFileMetadata(FileMetadata fileMetadata) {
        Version latestVersion = fileMetadata.getLatestVersion();
        return new FileSummaryDTO(
                fileMetadata.getName(),
                latestVersion.getSaveDate(),
                latestVersion.getAuthor().getFullName(),
                fileMetadata.getTask().getName()
        );
    }
}
