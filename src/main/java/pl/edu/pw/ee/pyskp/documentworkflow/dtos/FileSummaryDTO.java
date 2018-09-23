package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class FileSummaryDTO {
    @NonNull
    String name;

    @NonNull
    OffsetDateTime saveDate;

    @NonNull
    String author;

    @NonNull
    String taskName;
}
