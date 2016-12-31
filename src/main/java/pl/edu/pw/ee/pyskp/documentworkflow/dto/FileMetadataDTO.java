package pl.edu.pw.ee.pyskp.documentworkflow.dto;

/**
 * Created by piotr on 31.12.16.
 */
public class FileMetadataDTO {
    private String name = "";
    private String description = "";
    private String contentType = "";

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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
