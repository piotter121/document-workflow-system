package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Data
public class FileContentDTO {
    @NonNull
    private final List<String> lines;
}
