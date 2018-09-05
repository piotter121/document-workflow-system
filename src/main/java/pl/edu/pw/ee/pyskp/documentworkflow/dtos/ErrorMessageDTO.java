package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorMessageDTO {
    @NonNull
    private final String errorCode;
    private Map<String, String> params = new HashMap<>();
}
