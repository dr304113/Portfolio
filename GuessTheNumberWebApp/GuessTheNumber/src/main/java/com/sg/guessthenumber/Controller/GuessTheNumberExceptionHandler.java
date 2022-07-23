package com.sg.guessthenumber.Controller;

import com.sg.guessthenumber.service.DataValidationException;
import com.sg.guessthenumber.service.EmptyDataException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author dr304
 */
@ControllerAdvice
@RestController
public class GuessTheNumberExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String CONSTRAINT_MESSAGE = "Error: Please check data"
            + " entry and try again. ";

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Error> handleSqlException(
            SQLIntegrityConstraintViolationException ex, WebRequest request) {
        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);

        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Error> handleSqlException(
            EmptyResultDataAccessException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataValidationException.class)
    public final ResponseEntity<Error> handleDataValidationException(
            DataValidationException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage(CONSTRAINT_MESSAGE);
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyDataException.class)
    public final ResponseEntity<Error> handleEmptyDataException(
            EmptyDataException ex, WebRequest request) {

        Error err = new Error();
        err.setMessage("Well, uh, this is awkward... Nothing to see here");
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
