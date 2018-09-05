package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("WeakerAccess")
@Getter
@AllArgsConstructor
public class FieldErrorDTO {
    private String field, message;
}
