package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import java.util.Date;

/**
 * Created by piotr on 31.12.16.
 */
public class FileMetadataDTO {
    private long id;
    private String name = "";
    private String description = "";
    private String contentType = "";
    private boolean confirmed;
    private boolean markedToConfirm;
    private Date modificationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileMetadataDTO dto = (FileMetadataDTO) o;

        return id == dto.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
