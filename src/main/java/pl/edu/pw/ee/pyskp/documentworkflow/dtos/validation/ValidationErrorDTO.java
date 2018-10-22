package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class ValidationErrorDTO {
    @Getter
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }
}
