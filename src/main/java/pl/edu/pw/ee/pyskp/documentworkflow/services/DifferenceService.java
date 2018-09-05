package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * Created by piotr on 06.01.17.
 */
public interface DifferenceService {
    List<Difference> createDifferencesForNewFile(InputStream inputStream) throws IOException;

    List<Difference> getDifferencesBetweenTwoFiles(
            InputStream inputStream, InputStream anotherInputStream) throws IOException;

}
