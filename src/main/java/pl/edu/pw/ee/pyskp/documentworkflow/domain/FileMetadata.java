package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "taskId"})
})
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType contentType;

    @Column(nullable = false)
    private boolean confirmed = false;

    @Column(nullable = false)
    private boolean markedToConfirm = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "fileMetadata", cascade = CascadeType.ALL)
    private List<Version> versions;

    @Transient
    public Date getModificationDate() {
        return getLatestVersion().getSaveDate();
    }

    @Transient
    public Version getLatestVersion() {
        return versions.stream()
                .max(comparing(Version::getSaveDate))
                .orElseThrow(() -> new RuntimeException("Cannot find latest Version of FileMetadata!"));
    }

    @Transient
    public Date getCreationDate() {
        return versions.stream().map(Version::getSaveDate).min(Date::compareTo)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Could not find creation date of file with id=%d", id)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isMarkedToConfirm() {
        return markedToConfirm;
    }

    public void setMarkedToConfirm(boolean markedToConfirm) {
        this.markedToConfirm = markedToConfirm;
    }

}
