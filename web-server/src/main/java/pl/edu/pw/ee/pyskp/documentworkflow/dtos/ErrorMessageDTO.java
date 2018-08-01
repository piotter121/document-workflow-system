package pl.edu.pw.ee.pyskp.documentworkflow.dtos;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorMessageDTO {
    private String errorCode;
    private Map<String, String> params = new HashMap<>();
}
