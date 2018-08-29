package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by piotr on 07.01.17.
 */
@NoArgsConstructor
@Data
public class VersionInfoDTO {
    private List<DifferenceInfoDTO> differences;
    private String message;
    private UserInfoDTO author;
    private Date saveDate;
    private String versionString;
    private String previousVersionString;

    public VersionInfoDTO(Version version) {
        setAuthor(new UserInfoDTO(version.getAuthor()));
        setSaveDate(version.getSaveDate());
        setVersionString(version.getVersionString());
        setMessage(version.getMessage());
        setDifferences(version.getDifferences().stream()
                .map(DifferenceInfoDTO::new)
                .collect(toList()));
    }
}
