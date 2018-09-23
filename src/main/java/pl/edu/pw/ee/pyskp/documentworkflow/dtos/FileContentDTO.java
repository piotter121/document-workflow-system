package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Value
public class FileContentDTO {
    @NonNull
    List<String> lines;
}
