package pl.edu.pw.ee.pyskp.documentworkflow.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by piotr on 20.01.17.
 */
@RequiredArgsConstructor
@Service
public class TikaService {
    @NonNull
    private final Tika tika;

    public List<String> extractLines(InputStream inputStream) throws IOException {
        Reader parsingReader = tika.parse(inputStream);
        try (BufferedReader contentReader = new BufferedReader(parsingReader)) {
            return contentReader.lines().collect(Collectors.toList());
        }
    }


    public String detectMediaType(byte[] bytes) {
        return tika.detect(bytes);
    }
}
