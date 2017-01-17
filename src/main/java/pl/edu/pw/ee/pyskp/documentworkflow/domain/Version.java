package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"versionString", "fileMetadataId"})
})
public class Version {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date saveDate;

    @Column(nullable = false, length = 20)
    private String versionString;

    @Column(length = 1024)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authorId", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "fileMetadataId", nullable = false)
    private FileMetadata fileMetadata;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contentId", nullable = false)
    private FileContent fileContent;

    @Column(nullable = false, length = 64)
    private String checkSum;

    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL)
    private List<Difference> differences;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public FileContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(FileContent fileContent) {
        this.fileContent = fileContent;
    }

    public List<Difference> getDifferences() {
        return differences;
    }

    public void setDifferences(List<Difference> differences) {
        this.differences = differences;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public FileMetadata getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(FileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }
}
