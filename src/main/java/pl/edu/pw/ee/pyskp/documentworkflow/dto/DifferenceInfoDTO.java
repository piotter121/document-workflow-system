package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.DifferenceType;

/**
 * Created by p.pysk on 09.01.2017.
 */
public class DifferenceInfoDTO {
    private long previousSectionStart;
    private long previousSectionSize;
    private long newSectionStart;
    private long newSectionSize;
    private DifferenceType differenceType;

    public DifferenceType getDifferenceType() {
        return differenceType;
    }

    public void setDifferenceType(DifferenceType differenceType) {
        this.differenceType = differenceType;
    }

    public long getPreviousSectionStart() {
        return previousSectionStart;
    }

    public void setPreviousSectionStart(long previousSectionStart) {
        this.previousSectionStart = previousSectionStart;
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
