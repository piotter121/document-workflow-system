package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

import java.util.Set;

/**
 * Created by piotr on 20.01.17.
 */
@Data
public class DiffData {
    private FileContentDTO oldContent;
    private FileContentDTO newContent;
    private Set<Difference> differences;
}
