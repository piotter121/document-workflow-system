package pl.edu.pw.ee.pyskp.documentworkflow.service;

import pl.edu.pw.ee.pyskp.documentworkflow.domain.Difference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by piotr on 06.01.17.
 */
public interface DifferenceService {
    List<Difference> createDifferencesForNewFile(InputStream inputStream) throws IOException;
}
