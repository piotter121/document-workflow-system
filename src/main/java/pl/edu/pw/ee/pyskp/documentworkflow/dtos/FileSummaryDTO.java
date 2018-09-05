package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileMetadata;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
@Data
public class FileSummaryDTO {
    @NonNull
    private final String name;

    @NonNull
    private final Date saveDate;

    @NonNull
    private final String author;

    @NonNull
    private final String taskName;

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
