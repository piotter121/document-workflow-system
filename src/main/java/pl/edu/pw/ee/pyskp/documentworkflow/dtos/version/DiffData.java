package pl.edu.pw.ee.pyskp.documentworkflow.dtos.version;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.file.FileContentDTO;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Value
@ToString(onlyExplicitlyIncluded = true)
public final class DiffData {
    @NonNull
    @ToString.Include
    List<Difference> differences;

    @NonNull
    FileContentDTO newContent;

    FileContentDTO oldContent;
}
