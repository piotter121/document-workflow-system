package pl.edu.pw.ee.pyskp.documentworkflow.services;

import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;

import java.util.List;

/**
 * Created by piotr on 06.01.17.
 */
public interface DifferenceService {
    List<Difference> createDifferencesForNewFile(List<String> newFileLines);

    List<Difference> getDifferencesBetweenTwoFiles(List<String> previousVersionLines, List<String> nextVersionLines);
}
