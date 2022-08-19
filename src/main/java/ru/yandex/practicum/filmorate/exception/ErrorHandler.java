package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity handlerBadValidation(final ValidationException e) {
        log.error("Возникла ошибка валидации");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler
    public ResponseEntity handlerNotFound(final NotFoundException e) {
        log.error("Возникла ошибка не найденного объекта");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity handlerConstraintViolation(final ConstraintViolationException ex){
        log.error("Возникла ошибка валидации");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity handlerThrowable(final Throwable ex){
        log.error("Возникла исключение");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
