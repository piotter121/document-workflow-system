package pl.edu.pw.ee.pyskp.documentworkflow.service;

import org.apache.log4j.Logger;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.FileContentDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.utils.TikaUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by piotr on 20.01.17.
 */
public interface FileContentService {
    Logger logger = Logger.getLogger(FileContentService.class);

    Optional<FileContent> getOneById(long id);

    static FileContentDTO mapToFileContentDTO(FileContent fileContent) {
        FileContentDTO dto = new FileContentDTO();
        List<String> lines;
        try {
            lines = TikaUtils.extractLines(new ByteArrayInputStream(fileContent.getContent()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        dto.setLines(lines);
        return dto;
    }
}
