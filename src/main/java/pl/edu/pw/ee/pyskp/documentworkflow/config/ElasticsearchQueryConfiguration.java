package pl.edu.pw.ee.pyskp.documentworkflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Component
@ConfigurationProperties("dws.elasticsearch.query")
@Validated
public class ElasticsearchQueryConfiguration {
    @Valid
    private final HighlightConfig highlight = new HighlightConfig();

    @NotBlank
    private String indexName, typeName;

    @Data
    public static class HighlightConfig {
        @NotBlank
        private String encoder, tagsSchema;
    }
}
