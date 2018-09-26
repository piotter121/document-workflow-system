package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "file_metadata")
public class FileMetadata {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    @Column(name = "marked_to_confirm", nullable = false)
    private Boolean markedToConfirm = false;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @Embedded
    private VersionSummary latestVersion;
}
