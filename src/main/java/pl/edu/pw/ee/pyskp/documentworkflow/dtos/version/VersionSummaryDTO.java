package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.VersionSummary;

import java.sql.Timestamp;

@Value
public class VersionSummaryDTO {
    @NonNull
    String version, author;

    @NonNull
    Timestamp saveDate;

    public static VersionSummaryDTO fromVersionSummaryEntity(VersionSummary versionSummary) {
        return new VersionSummaryDTO(
                versionSummary.getVersionString(),
                versionSummary.getModificationAuthor().getFullName(),
                versionSummary.getSaveDate()
        );
    }
}
