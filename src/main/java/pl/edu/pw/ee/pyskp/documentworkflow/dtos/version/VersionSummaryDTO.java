package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Version;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VersionSummaryDTO {
    @NonNull
    @EqualsAndHashCode.Include
    String version;

    @NonNull
    @ToString.Exclude
    String author;

    long saveDate;

    public static VersionSummaryDTO fromVersion(Version version) {
        return new VersionSummaryDTO(
                version.getVersionString(),
                version.getAuthor().getFullName(),
                version.getSaveDate().getTime()
        );
    }
}
