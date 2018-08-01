package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileContentDTO {
    private List<String> lines = new ArrayList<>();

    public String getLine(int i) {
        return lines.get(i - 1);
    }
}
