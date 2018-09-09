package pl.edu.pw.ee.pyskp.documentworkflow.config;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@ConfigurationProperties("dws.security.cors")
@Validated
public class CorsConfig {
    @NotBlank
    private String path;

    @NotEmpty
    private List<String> allowedOrigins;

    @NotEmpty
    private List<String> allowedHeaders;

    @NotEmpty
    private List<String> allowedMethods;
}
