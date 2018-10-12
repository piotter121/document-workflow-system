package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.VersionSummary;

@Value
public class VersionSummaryDTO {
    @NonNull
    String version, author;

    long saveDate;

    public static VersionSummaryDTO fromVersionSummaryEntity(VersionSummary versionSummary) {
        return new VersionSummaryDTO(
                versionSummary.getVersionString(),
                versionSummary.getModificationAuthor().getFullName(),
                versionSummary.getSaveDate().getTime()
        );
    }
}
