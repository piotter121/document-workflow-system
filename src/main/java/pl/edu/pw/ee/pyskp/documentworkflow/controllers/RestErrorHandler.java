package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.error.ErrorMessageDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation.ValidationErrorDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnsupportedContentType;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class RestErrorHandler {
    @NonNull
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        log.error("Processing method argument not valid exception", ex);
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();
        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        }
        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            if (fieldErrorCodes != null) {
                localizedErrorMessage = fieldErrorCodes[0];
            }
        }
        return localizedErrorMessage;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Constraint violation exception occurred", e);
        ValidationErrorDTO dto = new ValidationErrorDTO();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            dto.addFieldError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }
        return dto;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessageDTO handleNotFoundException(ResourceNotFoundException ex) {
        log.error("Processing resource not found exception", ex);
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ex.getClass().getSimpleName());
        errorMessage.getParams().putAll(ex.getMessageParams());
        return errorMessage;
    }

    @ExceptionHandler(UnsupportedContentType.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessageDTO handleUnknownContentType(UnsupportedContentType ex) {
        log.error(ex.getLocalizedMessage(), ex);
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(ex.getClass().getSimpleName());
        errorMessageDTO.getParams().put("unsupportedContentTypeName", ex.getUnsupportedContentTypeName());
        return errorMessageDTO;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessageDTO handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Processing access denied exception", ex);
        return new ErrorMessageDTO(ex.getClass().getSimpleName());
    }
}
