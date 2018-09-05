package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Data
public class DiffData {
    @NonNull
    private final FileContentDTO newContent;

    @NonNull
    private final List<Difference> differences;

    private FileContentDTO oldContent;
}
