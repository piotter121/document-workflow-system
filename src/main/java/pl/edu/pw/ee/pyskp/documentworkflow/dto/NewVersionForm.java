package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by piotr on 19.01.17.
 */
public class NewVersionForm {
    private long fileId;
    private MultipartFile file;
    private String versionString;
    private String message;

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

