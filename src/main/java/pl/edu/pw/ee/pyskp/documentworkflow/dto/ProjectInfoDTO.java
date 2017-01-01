package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 29.12.16.
 */
public class ProjectInfoDTO {
    private String name = "";
    private String description = "";
    private UserInfoDTO administrator;
    private Date creationDate;
    private Date lastModified;
    private List<TaskInfoDTO> tasks;

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

    public int getNumberOfTasks() {
        return tasks == null ? 0 : tasks.size();
    }

    public UserInfoDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(UserInfoDTO administrator) {
        this.administrator = administrator;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<TaskInfoDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskInfoDTO> tasks) {
        this.tasks = tasks;
    }
}
