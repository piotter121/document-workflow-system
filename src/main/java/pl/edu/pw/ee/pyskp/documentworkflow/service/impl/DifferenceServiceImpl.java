package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import org.apache.tika.parser.ParsingReader;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Difference;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.DifferenceType;
import pl.edu.pw.ee.pyskp.documentworkflow.service.DifferenceService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 06.01.17.
 */
@Service
public class DifferenceServiceImpl implements DifferenceService {

    @Override
    public List<Difference> createDifferencesForNewFile(InputStream inputStream) throws IOException {
        List<String> lines = extractLines(inputStream);
        Patch<String> diff = DiffUtils.diff(new ArrayList<>(), lines);
        return diff.getDeltas().stream()
                .map(DifferenceServiceImpl::mapDeltaToDifference)
                .collect(Collectors.toList());
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

    private static List<String> extractLines(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader contentReader
                     = new BufferedReader(new ParsingReader(inputStream))) {
            String line;
            while ((line = contentReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
