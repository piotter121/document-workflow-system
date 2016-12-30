package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1024)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(updatable = false, nullable = false)
    private Date creationDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "administratorId")
    private User administrator;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @Transient
    public Optional<Date> getLastModified() {
        Date lastModified = null;
        for (Task task : tasks) {
            Optional<Date> taskModificationDate = task.getModificationDate();
            if (taskModificationDate.isPresent()) {
                Date modificationDate = taskModificationDate.get();
                if (lastModified == null || modificationDate.after(lastModified))
                    lastModified = modificationDate;
            }
        }
        return Optional.ofNullable(lastModified);
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

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
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
}
