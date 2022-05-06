package edu.cmu.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: [global handler for exception]
 * @author: [ysx]
 * @version: [v1.0]
 * @createTime: [2022/5/5 15:42]
 * @updateUser: [ysx]
 * @updateTime: [2022/5/5 15:42]
 * @updateRemark: [initial commit]
 */

@Slf4j
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ParseException.class)
    public ResponseEntity<ErrorResponse> parseExceptionHandler(ParseException e) {
        ErrorResponse response = new ErrorResponse("internal server error");
        log.error("time parse exception --------------{}" , e.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException e) {
//        ErrorResponse response = new ErrorResponse("internal server error");
//        log.error("runtime exception --------------{}" , e.getMessage());
//        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> queryParameterNotValidHandler(ConstraintViolationException e) {
        ErrorResponse response = new ErrorResponse("query parameter missing or not valid");
        log.error("query parameter not valid --------------{}" , e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WebExchangeBindException.class)
    public ResponseEntity<List<String>> postArgumentNotValidHandler(WebExchangeBindException e) {
        log.error("post request body argument not valid exception --------------{}" , e.getMessage());
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse("request has illegal argument");
        log.error("Illegal Argument Exception --------------{}", e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}
