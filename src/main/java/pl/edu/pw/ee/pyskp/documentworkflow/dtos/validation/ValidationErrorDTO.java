package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public final class ValidationErrorDTO {
    List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }
}
