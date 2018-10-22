package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.user.UserInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

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

    public static VersionInfoDTO fromVersion(Version version) {
        return fromVersion(version, null);
    }

    public static VersionInfoDTO fromVersion(Version version, String previousVersionString) {
        return new VersionInfoDTO(
                version.getMessage(),
                UserInfoDTO.fromUser(version.getAuthor()),
                version.getSaveDate().getTime(),
                version.getVersionString(),
                previousVersionString,
                version.getDifferences().stream()
                        .map(DifferenceInfoDTO::fromDifference)
                        .collect(Collectors.toList())
        );
    }
}
