package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ValidationErrorDTO {
    @Getter
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }
}
