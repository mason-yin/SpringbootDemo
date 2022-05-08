package edu.cmu.demo.exception;

import edu.cmu.demo.util.Response;
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
    public ResponseEntity<Response> parseExceptionHandler(ParseException e) {
        log.error("time parse exception --------------{}" , e.getMessage());
        return new ResponseEntity(new Response("time parser error", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Response> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtime exception --------------{}" , e.getMessage());
        return new ResponseEntity(new Response("runtime exception", null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Response> queryParameterNotValidHandler(ConstraintViolationException e) {
        log.error("query parameter not valid --------------{}" , e.getMessage());
        return new ResponseEntity(new Response("query parameter missing or not valid", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WebExchangeBindException.class)
    public ResponseEntity<Response> postArgumentNotValidHandler(WebExchangeBindException e) {
        log.error("post request body argument not valid exception --------------{}" , e.getMessage());
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String error = "";
        for (String err : errors) {
            error += err + ", ";
        }
        return new ResponseEntity(new Response(error, null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Response> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("Illegal Argument Exception --------------{}", e.getMessage());
        return new ResponseEntity(new Response("request has illegal argument", null), HttpStatus.BAD_REQUEST);
    }

}
