package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by piotr on 07.01.17.
 */
@Value
public class VersionInfoDTO {
    @NonNull
    String message;

    @NonNull
    UserInfoDTO author;

    @NonNull
    OffsetDateTime saveDate;

    @NonNull
    String versionString;

    String previousVersionString;

    List<DifferenceInfoDTO> differences;
}
