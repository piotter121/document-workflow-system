package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileSummaryDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String name;

    @NonNull
    Timestamp saveDate;

    @NonNull
    String author;

    @NonNull
    @EqualsAndHashCode.Include
    String taskName;
}
