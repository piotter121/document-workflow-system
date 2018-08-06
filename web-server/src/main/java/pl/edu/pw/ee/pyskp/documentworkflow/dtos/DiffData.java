package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.DifferenceType;

import java.util.Set;

/**
 * Created by piotr on 20.01.17.
 */
@Data
public class DiffData {
    private FileContentDTO oldContent;
    private FileContentDTO newContent;
    private Set<Difference> differences;

    public String getDiffTypeForNewVersion(int line) {
        Difference difference = findDifferenceForLineInNewContent(line);
        if (difference != null) {
            return difference.getDifferenceType().name();
        }
        return "";
    }

    private Difference findDifferenceForLineInNewContent(int line) {
        for (Difference difference : differences) {
            if (!difference.getDifferenceType().equals(DifferenceType.DELETE)) {
                long newSectionSize = difference.getNewSectionSize();
                long newSectionStart = difference.getNewSectionStart();
                long newSectionEnd = newSectionStart + newSectionSize;
                if (newSectionSize != 0) {
                    if (line > newSectionStart && line <= newSectionEnd) {
                        return difference;
                    }
                }
            }
        }
        return null;
    }

    public String getDiffTypeForOldVersion(int line) {
        Difference difference = findDifferenceForLineInOldContent(line);
        if (difference != null) {
            return difference.getDifferenceType().name();
        }
        return "";
    }

    private Difference findDifferenceForLineInOldContent(int line) {
        for (Difference difference : differences) {
            if (!difference.getDifferenceType().equals(DifferenceType.INSERT)) {
                long previousSectionSize = difference.getPreviousSectionSize();
                long previousSectionStart = difference.getPreviousSectionStart();
                long previousSectionEnd = previousSectionStart + previousSectionSize;
                if (previousSectionSize != 0) {
                    if (line > previousSectionStart && line <= previousSectionEnd) {
                        return difference;
                    }
                }
            }
        }
        return null;
    }

}
