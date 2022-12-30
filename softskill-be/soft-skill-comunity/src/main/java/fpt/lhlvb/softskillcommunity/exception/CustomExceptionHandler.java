package fpt.lhlvb.softskillcommunity.exception;

import fpt.lhlvb.softskillcommunity.common.CommonConstant;
import fpt.lhlvb.softskillcommunity.model.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@SuppressWarnings({ "unchecked", "rawtypes" })
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, String> errors;

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        errors = new HashMap<>();
        errors.put(CommonConstant.RECORD_NOT_FOUND, ex.getLocalizedMessage());
        ex.printStackTrace();
        ErrorResponse error = new ErrorResponse(CommonConstant.HTTP_CODE_404, new Date(), CommonConstant.RECORD_NOT_FOUND,
                errors);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse(CommonConstant.HTTP_CODE_400, new Date(), ex.getLocalizedMessage(), errors);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        errors = new HashMap<>();
        ErrorResponse error = new ErrorResponse(CommonConstant.HTTP_CODE_500, new Date(), ex.getMessage(), errors);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
