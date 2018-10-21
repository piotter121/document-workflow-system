package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileSummary;

import java.sql.Timestamp;

@Value
public class FileSummaryDTO {
    @NonNull
    String name;

    @NonNull
    Timestamp saveDate;

    @NonNull
    String author;

    @NonNull
    String taskName;

    public static FileSummaryDTO fromFileSummary(FileSummary fileSummary) {
        return new FileSummaryDTO(
                fileSummary.getName(),
                new Timestamp(fileSummary.getModificationDate().getTime()),
                fileSummary.getModificationAuthor().getFullName(),
                fileSummary.getTaskName()
        );
    }
}
