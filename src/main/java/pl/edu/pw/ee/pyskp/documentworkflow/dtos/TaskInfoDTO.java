package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
@Value
public class TaskInfoDTO {
    @NonNull
    String id;

    @NonNull
    String name;

    String description;

    @NonNull
    String projectId, projectName;

    @NonNull
    UserInfoDTO projectAdministrator, administrator;

    @NonNull
    Timestamp creationDate, modificationDate;

    List<UserInfoDTO> participants;

    FileSummaryDTO lastModifiedFile;

    List<FileMetadataDTO> filesInfo;
}
