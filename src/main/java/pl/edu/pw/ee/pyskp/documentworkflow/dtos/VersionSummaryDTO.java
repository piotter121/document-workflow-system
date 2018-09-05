package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

import java.util.Date;

@SuppressWarnings("WeakerAccess")
@Data
public class VersionSummaryDTO {
    public static VersionSummaryDTO fromVersion(Version version) {
        return new VersionSummaryDTO(
                version.getVersionString(),
                version.getAuthor().getFullName(),
                version.getSaveDate()
        );
    }

    @NonNull
    private final String version, author;

    @NonNull
    private final Date saveDate;
}
