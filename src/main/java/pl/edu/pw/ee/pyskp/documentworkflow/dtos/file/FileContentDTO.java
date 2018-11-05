package pl.edu.pw.ee.pyskp.documentworkflow.dtos.file;

import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Value
public class FileContentDTO {
    @ToString.Exclude
    @NonNull
    List<String> lines;
}
