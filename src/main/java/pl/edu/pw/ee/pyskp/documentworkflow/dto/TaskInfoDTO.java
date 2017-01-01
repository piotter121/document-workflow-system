package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import java.util.List;

/**
 * Created by piotr on 31.12.16.
 */
public class TaskInfoDTO {
    private String name;
    private String description;
    private UserInfoDTO administrator;
    private List<FileMetadataDTO> filesInfo;

    public int getNumberOfFiles() {
        return filesInfo == null ? 0 : filesInfo.size();
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

    public UserInfoDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(UserInfoDTO administrator) {
        this.administrator = administrator;
    }

    public List<FileMetadataDTO> getFilesInfo() {
        return filesInfo;
    }

    public void setFilesInfo(List<FileMetadataDTO> filesInfo) {
        this.filesInfo = filesInfo;
    }
}
