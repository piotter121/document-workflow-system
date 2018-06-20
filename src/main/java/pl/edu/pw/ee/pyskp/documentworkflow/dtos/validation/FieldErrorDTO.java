package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldErrorDTO {
    private String field, message;
}
