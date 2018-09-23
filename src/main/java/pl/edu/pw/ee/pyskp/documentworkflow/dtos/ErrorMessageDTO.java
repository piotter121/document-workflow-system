package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Value;

import java.util.HashMap;
import java.util.Map;

@Value
public class ErrorMessageDTO {
    String errorCode;
    Map<String, String> params = new HashMap<>();
}
