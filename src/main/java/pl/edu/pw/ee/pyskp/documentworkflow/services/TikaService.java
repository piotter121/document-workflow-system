package pl.edu.pw.ee.pyskp.documentworkflow.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class TikaService {
    @NonNull
    private final Tika tika;

    public List<String> extractParagraphs(InputStream inputStream) throws IOException {
        List<String> lines = new ArrayList<>();

        Reader parsingReader = tika.parse(inputStream);
        try (BufferedReader contentReader = new BufferedReader(parsingReader)) {
            String line;
            while ((line = contentReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }


    public String detectMediaType(byte[] bytes) {
        return tika.detect(bytes);
    }
}
