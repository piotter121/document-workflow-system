package pl.edu.pw.ee.pyskp.documentworkflow.data.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private OffsetDateTime creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "administrator_id", nullable = false)
    private User administrator;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "task_participant",
            joinColumns = @JoinColumn(name = "task_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "participant_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"participant_id", "task_id"})
    )
    @OrderBy("firstName ASC, lastName ASC")
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    @OrderBy("latestVersion.saveDate DESC")
    private List<FileMetadata> files = new ArrayList<>();
}
