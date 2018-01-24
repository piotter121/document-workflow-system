package pl.edu.pw.ee.pyskp.documentworkflow.services.impl;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.data.domain.DifferenceType;
import pl.edu.pw.ee.pyskp.documentworkflow.services.DifferenceService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.edu.pw.ee.pyskp.documentworkflow.utils.TikaUtils.extractLines;

/**
 * Created by piotr on 06.01.17.
 */
@Service
public class DifferenceServiceImpl implements DifferenceService {

    @Override
    public Set<Difference> createDifferencesForNewFile(InputStream inputStream) throws IOException {
        List<String> lines = extractLines(inputStream);
        Patch<String> diff = DiffUtils.diff(new ArrayList<>(), lines);
        return diff.getDeltas().stream()
                .map(DifferenceServiceImpl::mapDeltaToDifference)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Difference> getDifferencesBetweenTwoFiles(
            InputStream inputStream, InputStream anotherInputStream) throws IOException {
        Patch<String> diff = DiffUtils.diff(extractLines(inputStream), extractLines(anotherInputStream));
        return diff.getDeltas().stream()
                .map(DifferenceServiceImpl::mapDeltaToDifference)
                .collect(Collectors.toSet());
    }

    private static Difference mapDeltaToDifference(Delta<String> delta) {
        Chunk<String> original = delta.getOriginal();
        Chunk<String> revised = delta.getRevised();
        Difference difference = new Difference();
        difference.setPreviousSectionStart(original.getPosition());
        difference.setPreviousSectionSize(original.size());
        difference.setNewSectionStart(revised.getPosition());
        difference.setNewSectionSize(revised.size());
        DifferenceType differenceType = DifferenceType.fromDeltaType(delta.getType())
                .orElseThrow(() -> new IllegalArgumentException("Delta type is null"));
        difference.setDifferenceType(differenceType);
        return difference;
    }
}
