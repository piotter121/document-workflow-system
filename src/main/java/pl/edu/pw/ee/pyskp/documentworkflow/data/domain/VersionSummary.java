package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class VersionSummary {
    @Column(name = "version_string", nullable = false)
    private String versionString;

    @Column(name = "save_date", nullable = false)
    private Timestamp saveDate;

    @ManyToOne
    @JoinColumn(name = "modification_author_id", nullable = false)
    private User modificationAuthor;
}
