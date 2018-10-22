package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

@Value
public class VersionSummaryDTO {
    @NonNull
    String version, author;

    long saveDate;

    public static VersionSummaryDTO fromVersion(Version version) {
        return new VersionSummaryDTO(
                version.getVersionString(),
                version.getAuthor().getFullName(),
                version.getSaveDate().getTime()
        );
    }
}
