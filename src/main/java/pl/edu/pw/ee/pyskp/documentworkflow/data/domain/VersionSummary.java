package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Embeddable
public class VersionSummary {
    @EqualsAndHashCode.Include
    @Column(name = "version_string", nullable = false)
    private String versionString;

    @Column(name = "save_date", nullable = false)
    private Timestamp saveDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "modification_author_id", nullable = false)
    private User modificationAuthor;
}
