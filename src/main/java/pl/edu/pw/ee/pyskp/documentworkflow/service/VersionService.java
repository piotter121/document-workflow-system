package pl.edu.pw.ee.pyskp.documentworkflow.service;

import org.apache.tika.exception.TikaException;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;

import java.io.IOException;

/**
 * Created by piotr on 06.01.17.
 */
public interface VersionService {
    Version createUnmanagedInitVersionOfFile(NewFileForm form) throws IOException, TikaException;
}
