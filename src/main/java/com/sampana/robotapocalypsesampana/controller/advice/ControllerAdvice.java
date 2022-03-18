package com.sampana.robotapocalypsesampana.controller.advice;


import com.sampana.robotapocalypsesampana.exception.NotFoundException;
import com.sampana.robotapocalypsesampana.exception.RequestAlreadyPerformedException;
import com.sampana.robotapocalypsesampana.exception.SystemException;
import com.sampana.robotapocalypsesampana.model.Error;
import com.sampana.robotapocalypsesampana.model.Response;
import com.sampana.robotapocalypsesampana.model.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Demilade Oladugba on 3/17/2022
 */
@org.springframework.web.bind.annotation.ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return commonResponseForBadRequest("Data integrity violation", e);
    }

    @ExceptionHandler(RequestAlreadyPerformedException.class)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ResponseEntity<Response<?>> handleRequestAlreadyPerformedException(RequestAlreadyPerformedException e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.Successful.code);
        response.setResponseMessage(e.getMessage());
        log.error("Error information -> " + e.getMessage() + "\n ");
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleValidationException(MethodArgumentNotValidException e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.BadRequest.code);
        response.setResponseMessage("Bad request");
        BindingResult result = e.getBindingResult();
        List<FieldError> errorList = result.getFieldErrors();
        List<Error> errors = errorList.stream().map(fieldError -> new Error(fieldError.getField(), fieldError.getDefaultMessage())).collect(Collectors.toList());
        response.setErrors(errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleBindingException(BindException e) {
        return commonResponseForBadRequest("Invalid url parameters", e);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        return commonResponseForBadRequest("Required input file is missing", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<?>> handleException(Exception e) {
        return commonResponseForSystemError("Error occurred, please contact the developer", e);
    }

    @ExceptionHandler(SystemException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Response<?>> handleSystemException(SystemException e) {
        return commonResponseForSystemError(e.getMessage(), e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return commonResponseForBadRequest("Invalid url", e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Response<?>> handleNotFoundException(NotFoundException e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.NotFound.code);
        response.setResponseMessage(e.getMessage());
        log.error("Error information -> " + e.getMessage() + "\n ");
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Response<?>> handleIOException(IOException e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.BadRequest.code);
        response.setResponseMessage(e.getMessage());
        log.error(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    @ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<Response<?>> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.SystemError.code);
        response.setResponseMessage("Request timed out");
        log.error("Error information -> " + e.getMessage() + "\n ");
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return commonResponseForBadRequest(e.getMessage(), e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return commonResponseForBadRequest("Request not supported", e);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return commonResponseForBadRequest("Json Parse Error", e);
    }

    private ResponseEntity<Response<?>> commonResponseForBadRequest(String message, Exception e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.BadRequest.code);
        response.setResponseMessage(message);
        log.error(message);
        log.error("Error information -> " + e.getMessage() + "\n ");
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> commonResponseForSystemError(String message, Exception e) {
        Response<?> response = new Response<>();
        response.setResponseCode(ResponseCode.SystemError.code);
        response.setResponseMessage(message);
        log.error(e.getMessage());
        log.error("Error information -> " + e.getMessage() + "\n ");
        e.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
