package demo.web.app.filtersbackend.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Give clients a bit more info on what went wrong if their message could not
     * be parsed, but do not expose internal implementation details or full names.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Map<String, List<String>>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        String error = "Invalid request message";

        // see https://dougbreaux.github.io/2020/10/11/Additional-Jackson-Parse-Errors.html
        if (cause instanceof JsonParseException) {
            JsonParseException jpe = (JsonParseException) cause;
            error = jpe.getOriginalMessage();
        } else if (cause instanceof MismatchedInputException) {
            MismatchedInputException mie = (MismatchedInputException) cause;
            if (mie.getPath() != null && mie.getPath().size() > 0) {
                error = "Invalid request field: " + mie.getPath().get(0).getFieldName();
            }
        } else if (cause instanceof JsonMappingException) {
            JsonMappingException jme = (JsonMappingException) cause;
            error = jme.getOriginalMessage();
            if (jme.getPath() != null && jme.getPath().size() > 0) {
                error = "Invalid request field: " + jme.getPath().get(0).getFieldName() +
                        ": " + error;
            }
        }

        // currently, this handler can only return a single error, but a list is used
        // nonetheless to keep consistency with other handlers that are not bound by
        // this limitation
        List<String> errors = new ArrayList<>();
        errors.add(error);

        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * If a client sends a message with data that does not pass DTO field validation rules,
     * respond with a list of all invalid fields and explain the issues with them.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.add(fe.getField() + ": " + fe.getDefaultMessage());
        }

        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
