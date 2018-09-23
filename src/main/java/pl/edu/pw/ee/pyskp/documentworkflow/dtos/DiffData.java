package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Value
public final class DiffData {
    @NonNull
    private final List<Difference> differences;

    @NonNull
    private final FileContentDTO newContent;

    private FileContentDTO oldContent;
}
