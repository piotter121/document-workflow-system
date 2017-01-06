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

    private long previousSectionStart;
    private long previousSectionSize;
    private long newSectionStart;
    private long newSectionSize;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private DifferenceType differenceType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "versionId", nullable = false)
    private Version version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPreviousSectionStart() {
        return previousSectionStart;
    }

    public void setPreviousSectionStart(long previousSectionStart) {
        this.previousSectionStart = previousSectionStart;
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

    public long getPreviousSectionSize() {
        return previousSectionSize;
    }

    public void setPreviousSectionSize(long previousSectionSize) {
        this.previousSectionSize = previousSectionSize;
    }

    public long getNewSectionStart() {
        return newSectionStart;
    }

    public void setNewSectionStart(long newSectionStart) {
        this.newSectionStart = newSectionStart;
    }

    public long getNewSectionSize() {
        return newSectionSize;
    }

    public void setNewSectionSize(long newSectionSize) {
        this.newSectionSize = newSectionSize;
    }
}