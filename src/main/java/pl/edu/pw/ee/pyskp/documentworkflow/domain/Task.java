package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

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
}
