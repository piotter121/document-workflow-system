package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.FileSummary;

import java.util.Date;

@NoArgsConstructor
@Data
@SuppressWarnings("WeakerAccess")
public class FileSummaryDTO {
    private String name;
    private Date saveDate;
    private String author;
    private String taskName;

    FileSummaryDTO(FileSummary fileSummary) {
        name = fileSummary.getName();
        saveDate = fileSummary.getModificationDate();
        author = fileSummary.getModificationAuthor().getFullName();
        taskName = fileSummary.getTaskName();
    }
}
