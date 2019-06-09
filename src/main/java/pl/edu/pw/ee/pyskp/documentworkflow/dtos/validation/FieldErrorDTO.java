package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.NonNull;
import lombok.Value;

@Value
public class FieldErrorDTO {
    @NonNull
    String field, message;
}
