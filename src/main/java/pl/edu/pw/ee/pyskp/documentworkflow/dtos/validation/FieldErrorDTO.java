package pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation;

import lombok.NonNull;
import lombok.Value;

@SuppressWarnings("WeakerAccess")
@Value
public class FieldErrorDTO {
    @NonNull
    String field, message;
}
