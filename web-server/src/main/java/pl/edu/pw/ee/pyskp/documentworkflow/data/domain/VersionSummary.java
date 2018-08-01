package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.UserDefinedType;

import java.util.Date;

@NoArgsConstructor
@Data
@UserDefinedType("version_summary")
public class VersionSummary {
    private String version;

    @Column("save_date")
    private Date saveDate;

    @Column("modification_author")
    private UserSummary modificationAuthor;

    public VersionSummary(Version version) {
        this.version = version.getVersionString();
        this.saveDate = version.getSaveDate();
        this.modificationAuthor = version.getAuthor();
    }
}
