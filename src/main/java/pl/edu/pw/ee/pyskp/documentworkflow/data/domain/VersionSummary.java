package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class VersionSummary {
    @Column(name = "version_string")
    private String versionString;

    @Column(name = "save_date")
    private OffsetDateTime saveDate;

    @ManyToOne
    @JoinColumn(name = "modification_author_id", nullable = false)
    private User modificationAuthor;
}
