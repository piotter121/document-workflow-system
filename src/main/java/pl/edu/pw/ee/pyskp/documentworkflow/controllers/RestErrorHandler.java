package pl.edu.pw.ee.pyskp.documentworkflow.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.ErrorMessageDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.dtos.validation.ValidationErrorDTO;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.ResourceNotFoundException;
import pl.edu.pw.ee.pyskp.documentworkflow.exceptions.UnknownContentType;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ControllerAdvice
public class RestErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    @NonNull
    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        LOGGER.error("Processing method argument not valid exception", ex);
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
            localizedErrorMessage = fieldErrorCodes[0];
        }
        return localizedErrorMessage;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorDTO handleConstraintViolationException(ConstraintViolationException e) {
        LOGGER.error("Constraint violation exception occurred", e);
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
        LOGGER.error("Processing resource not found exception", ex);
        ErrorMessageDTO errorMessage = new ErrorMessageDTO(ex.getClass().getSimpleName());
        errorMessage.getParams().putAll(ex.getMessageParams());
        return errorMessage;
    }

    @ExceptionHandler(UnknownContentType.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorMessageDTO handleUnknownContentType(UnknownContentType ex) {
        LOGGER.error(ex.getLocalizedMessage(), ex);
        return new ErrorMessageDTO(ex.getClass().getSimpleName());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessageDTO handleAccessDeniedException(AccessDeniedException ex) {
        LOGGER.error("Processing access denied exception", ex);
        return new ErrorMessageDTO(ex.getClass().getSimpleName());
    }
}
