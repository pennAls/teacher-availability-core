package org.example.msteacher.exceptions;

import org.example.mssecurity.modules.users.domain.exceptions.EmailAlreadyExistsException;
import org.example.mssecurity.modules.users.domain.exceptions.UserNotFoundException;
import org.hibernate.mapping.Map;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.example.msteacher.modules.teacher.domain.exceptions.TeacherNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

}
    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Object> teacherNotFound(TeacherNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", 404,
                        "error", ex.getMessage()
                ));
    }
