package pl.edu.pw.ee.pyskp.documentworkflow.dtos.error;

import lombok.NonNull;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class ErrorMessageDTO {
    @NonNull
    String errorCode;

    Map<String, String> params = new HashMap<>();
}
