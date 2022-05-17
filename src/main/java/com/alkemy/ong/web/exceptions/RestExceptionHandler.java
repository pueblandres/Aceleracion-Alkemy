package com.alkemy.ong.web.exceptions;

import com.alkemy.ong.domain.exceptions.CommunicationException;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> HandleParamNotFound(RuntimeException ex, WebRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                Arrays.asList("Resource Not Found")
        );
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                Arrays.asList("Bad Request")
        );
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @ExceptionHandler(value = {CommunicationException.class, InternalAuthenticationServiceException.class})
    protected ResponseEntity<Object> HandleCommunicationException(RuntimeException ex, WebRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                Arrays.asList("Incorrect email or password")
        );
        return handleExceptionInternal(ex, errorDTO, new HttpHeaders(), errorDTO.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request){
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()){
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiErrorDTO apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),errors);
        return handleExceptionInternal(ex,apiError,headers,apiError.getStatus(),request);
    }

    @Data
    @AllArgsConstructor
    public static class ApiErrorDTO {

        private HttpStatus status;
        private String message;
        private List<String> errors;
    }
}
