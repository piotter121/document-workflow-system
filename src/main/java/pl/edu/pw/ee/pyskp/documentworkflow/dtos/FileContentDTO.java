package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import pl.edu.pw.ee.pyskp.documentworkflow.utils.TikaUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Data
@NoArgsConstructor
public class FileContentDTO {
    private static final Logger LOGGER = LogManager.getLogger(FileContentDTO.class);

    public FileContentDTO(byte[] fileContent) {
        try {
            lines = TikaUtils.extractLines(new ByteArrayInputStream(fileContent));
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw new RuntimeException(exception);
        }
    }

    private List<String> lines = new ArrayList<>();

    public String getLine(int i) {
        return lines.get(i - 1);
    }
}
