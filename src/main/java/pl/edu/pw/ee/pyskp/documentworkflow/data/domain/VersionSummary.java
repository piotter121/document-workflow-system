package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;

@UserDefinedType
@Data
@NoArgsConstructor
public class VersionSummary {
    private String version;

    private Date saveDate;

    private UserSummary modificationAuthor;

    public VersionSummary(Version version) {
        this.version = version.getVersionString();
        this.saveDate = version.getSaveDate();
        this.modificationAuthor = version.getAuthor();
    }
}
