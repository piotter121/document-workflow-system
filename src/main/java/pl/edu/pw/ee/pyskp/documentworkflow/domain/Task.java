package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "projectId"})
})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(length = 1024)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDate;

    @OneToMany(mappedBy = "task")
    private List<FileMetadata> files;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "administratorId")
    private User administrator;

    @ManyToMany
    @JoinTable(name = "task_user", joinColumns = {
            @JoinColumn(name = "taskId", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "userId", nullable = false)
    })
    private List<User> participants;

    @Transient
    public Optional<FileMetadata> getLastModifiedFile() {
        return files.stream()
                .max(comparing(FileMetadata::getModificationDate));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<FileMetadata> getFiles() {
        return files;
    }

    public void setFiles(List<FileMetadata> files) {
        this.files = files;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Optional<Date> getModificationDate() {
        return getLastModifiedFile().map(FileMetadata::getModificationDate);
    }
}
