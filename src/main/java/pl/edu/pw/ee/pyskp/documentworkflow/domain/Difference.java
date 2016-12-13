package pl.edu.pw.ee.pyskp.documentworkflow.domain;

import javax.persistence.*;

/**
 * Created by piotr on 11.12.16.
 */
@Entity
public class Difference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long position;

    @Enumerated(value = EnumType.STRING)
    private DifferenceType differenceType;

    @ManyToOne
    @JoinColumn(name = "versionId", nullable = false)
    private Version version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public DifferenceType getDifferenceType() {
        return differenceType;
    }

    public void setDifferenceType(DifferenceType differenceType) {
        this.differenceType = differenceType;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }
}