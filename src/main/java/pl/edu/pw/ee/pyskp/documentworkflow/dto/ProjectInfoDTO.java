package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by piotr on 29.12.16.
 */
public class ProjectInfoDTO {
    private long id;
    private String name = "";
    private String description = "";
    private UserInfoDTO administrator;
    private Date creationDate;
    private TaskInfoDTO lastModifiedTask;
    private List<TaskInfoDTO> tasks;

    public int getNumberOfParticipants() {
        Set<UserInfoDTO> users = new HashSet<>();
        tasks.forEach(taskInfoDTO -> {
            users.addAll(taskInfoDTO.getParticipants());
            users.add(taskInfoDTO.getAdministrator());
        });
        users.add(getAdministrator());
        return users.size();
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

    public TaskInfoDTO getLastModifiedTask() {
        return lastModifiedTask;
    }

    public void setLastModifiedTask(TaskInfoDTO lastModifiedTask) {
        this.lastModifiedTask = lastModifiedTask;
    }

    public List<TaskInfoDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskInfoDTO> tasks) {
        this.tasks = tasks;
    }

    public int getNumberOfFiles() {
        return tasks == null
                ? 0
                : tasks.stream().mapToInt(TaskInfoDTO::getNumberOfFiles).sum();
    }
}
