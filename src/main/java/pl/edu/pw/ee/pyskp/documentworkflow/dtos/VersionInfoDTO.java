package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 07.01.17.
 */
@Data
public class VersionInfoDTO {
    public static VersionInfoDTO fromVersion(Version version) {
        return new VersionInfoDTO(
                version.getDifferences().stream()
                        .map(DifferenceInfoDTO::fromDifference)
                        .collect(Collectors.toList()),
                version.getMessage(),
                UserInfoDTO.fromUser(version.getAuthor()),
                version.getSaveDate(),
                version.getVersionString()
        );
    }

    private final List<DifferenceInfoDTO> differences;
    private final String message;
    private final UserInfoDTO author;
    private final Date saveDate;
    private final String versionString;
    private String previousVersionString;
}
