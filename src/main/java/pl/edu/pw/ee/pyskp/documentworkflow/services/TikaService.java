package pl.edu.pw.ee.pyskp.documentworkflow.services;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by piotr on 20.01.17.
 */
@Service
public class TikaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TikaService.class);

    private final Tika tika;

    public TikaService() {
        try {
            Resource resource = new ClassPathResource("tika-config.xml");
            TikaConfig config = new TikaConfig(resource.getInputStream());
            tika = new Tika(config);
        } catch (TikaException | IOException | SAXException e) {
            LOGGER.error("Exception occurred during Tika initialization", e);
            throw new RuntimeException(e);
        }
    }

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
