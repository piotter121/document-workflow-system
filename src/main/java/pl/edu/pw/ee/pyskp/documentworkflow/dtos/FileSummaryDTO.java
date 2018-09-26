package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

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
}
