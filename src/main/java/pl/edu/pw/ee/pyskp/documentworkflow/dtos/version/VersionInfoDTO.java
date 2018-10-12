package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

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

    long saveDate;

    @NonNull
    String versionString;

    String previousVersionString;

    List<DifferenceInfoDTO> differences;
}
