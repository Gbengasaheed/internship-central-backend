package com.example.demo.exceptions;

import com.example.demo.dtos.ErrorResponseDto;
import com.example.demo.dtos.ErrorResponseWithArgsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ControllerAdvice(basePackages = { "com.example.demo.controller"} )
@RequiredArgsConstructor
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(CommonsModuleException.class)
    public ResponseEntity<Object> handleExistingCustomerException(CommonsModuleException exception) {
        log.error("An expected exception was thrown :: ", exception);
        return ErrorResponseDto.build(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<Object> handleMessagingException(MessagingException exception) {
        log.error("An expected exception was thrown :: ", exception);
        return ErrorResponseDto.build(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        Locale locale = new Locale("en");
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(err -> messageSource.getMessage(err, locale))
                .toList();
        return new ResponseEntity<>(new ErrorMessage(errorMessages.toString()), HttpStatus.BAD_REQUEST);
    }



//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        BindingResult result = ex.getBindingResult();
//        List<FieldError> fieldErrors = result.getFieldErrors();
//        List<String> errors = new ArrayList<>();
//        for (FieldError fieldError : fieldErrors) {
//            String errorMessage = messageSource.getMessage(fieldError, null);
//            errors.add(errorMessage);
//        }
//        ErrorResponse errorResponse = new ErrorResponse("Validation failed", errors);
//        return ResponseEntity.badRequest().body(errorResponse);
//    }



    @NonNull
    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(@NonNull MissingPathVariableException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatus status,
                                                               @NonNull WebRequest request) {
        log.error("An expected exception was thrown :: ", ex);
        return ErrorResponseWithArgsDto.build(status,
                new ErrorResponseWithArgsDto.ErrorWithArguments("missing.request.path.variable",
                        new Object[]{ex.getVariableName()}));
    }
    public ResponseEntity<Object> handleRequestBodyException(List<FieldError> errors) {
        return ErrorResponseWithArgsDto.build(HttpStatus.BAD_REQUEST, errors);
    }
}