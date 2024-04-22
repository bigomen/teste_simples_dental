package com.simplesDental.teste.exception;

import com.simplesDental.teste.service.BaseService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler extends BaseService {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage notFoundExceptionHandler(NotFoundException exception){
        return ErrorMessage.builder()
                .userMessage(this.getMessage("runtime.exception.message"))
                .apiMessage(this.getMessage(exception.getMessage()) + " " + this.getMessage("notFound"))
                .time(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessage validationExeption(MethodArgumentNotValidException exception){
        List<String> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String message;
        if(erros.size() > 1){
            message = this.getMessage("error.requiredField.multiple");
            message = message.replace("{c}", String.join(",",erros.stream().map(this::getMessage).toList()));
        }else{
            message = this.getMessage("error.requiredField.unique");
            message = message.replace("{c}", this.getMessage(erros.get(0)));
        }

        return ErrorMessage.builder()
                .userMessage(message)
                .apiMessage(message)
                .time(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadUserException.class)
    public ErrorMessage badUserException(BadUserException badUserException){
        this.log(badUserException.getMessage());

        return ErrorMessage.builder()
                .userMessage(this.getMessage(badUserException.getMessage()))
                .apiMessage(this.getMessage(badUserException.getMessage()))
                .time(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage runTimeExceptionHandler(Exception exception){
        return ErrorMessage.builder()
                .userMessage(this.getMessage("runtime.exception.message"))
                .apiMessage(exception.getMessage())
                .time(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
