package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;

@Value
public class TaskSummaryDTO {
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private Timestamp creationDate;

    private FileSummaryDTO lastModifiedFile;

    private int numberOfFiles, numberOfParticipants;
}
