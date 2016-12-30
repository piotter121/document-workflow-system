package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class FileMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId", nullable = false)
    private Task task;

    @OneToMany(mappedBy = "fileMetadata", cascade = CascadeType.ALL)
    private List<Version> versions;

    @Transient
    public Optional<Version> getLatestVersion() {
        Version latestVersion = null;
        for (Version version : versions)
            if (latestVersion == null || version.getSaveDate().after(latestVersion.getSaveDate()))
                latestVersion = version;
        return Optional.ofNullable(latestVersion);
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
}
