package pl.edu.pw.ee.pyskp.documentworkflow.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
public class FileContentDTO {
    private List<String> lines = new ArrayList<>();

    public String getLine(int i) {
        return lines.get(i - 1);
    }

    public void setLine(int i, String line) {
        lines.set(i - 1, line);
    }

    public void setLines(List<String> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public List<String> getLines() {
        return lines;
    }
}
