package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.VersionSummary;

import java.util.Date;

@Data
@EqualsAndHashCode
class VersionSummaryDTO {
    private String version, author;
    private Date saveDate;

    VersionSummaryDTO(VersionSummary version) {
        this.version = version.getVersion();
        this.author = version.getModificationAuthor().getFullName();
        this.saveDate = version.getSaveDate();
    }
}
