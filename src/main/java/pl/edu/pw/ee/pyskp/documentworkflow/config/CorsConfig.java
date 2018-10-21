package pl.edu.pw.ee.pyskp.documentworkflow.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
