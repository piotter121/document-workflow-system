package pl.edu.pw.ee.pyskp.documentworkflow.utils;

import org.apache.tika.Tika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
public class TikaUtils {
    private final Tika tika = new Tika();

    public List<String> extractLines(InputStream inputStream) throws IOException {
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
}
