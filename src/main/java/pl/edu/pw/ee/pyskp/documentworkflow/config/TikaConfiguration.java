package pl.edu.pw.ee.pyskp.documentworkflow.config;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;

@Configuration
public class TikaConfiguration {
    @Bean
    public Tika tika() throws TikaException, IOException, SAXException {
        Resource resource = new ClassPathResource("tika-config.xml");
        TikaConfig tikaConfig = new TikaConfig(resource.getInputStream());
        return new Tika(tikaConfig);
    }
}
