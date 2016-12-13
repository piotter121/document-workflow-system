package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class FileContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] content;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "fileContent")
    private Version version;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}

