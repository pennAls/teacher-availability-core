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
import teacher.domain.exceptions.TeacherNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();

        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({DataAccessResourceFailureException.class, CannotCreateTransactionException.class})
    public ResponseEntity<ErrorResponse> handleDatabaseConnectionException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "SERVICE_UNAVAILABLE"
        );
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "MALFORMED_JSON"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_URL_PARAMETER"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFound(NoHandlerFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "ROUTE_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "METHOD_NOT_ALLOWED"
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "EMAIL_ALREADY_EXISTS"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(SchoolAlreadyExistsException.class)
    public ResponseEntity<Object> handleSchoolAlreadyExists(SchoolAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "code", 409,
                        "error", ex.getMessage()
                ));
    }
    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<Object> handleSchoolNotFoundException(SchoolNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "error", 404,
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(DisciplineAlreadyExistsException.class)
    public ResponseEntity<Object> handleDisciplineAlreadyExists(DisciplineAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", 404,
                        "error", ex.getMessage()
                ));
    }
    @ExceptionHandler(DisciplineNotFoundException.class)
    public ResponseEntity<Object> handleDisciplineNotFound(DisciplineNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", 404,
                        "error", ex.getMessage()
                ));
    }
    @ExceptionHandler(IesNotFoundException.class)
    public ResponseEntity<Object> handleIesNotFound(IesNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", 404,
                        "error", ex.getMessage()
                ));
    }
    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Object> teacherNotFound(TeacherNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", 404,
                        "error", ex.getMessage()
                ));
    }
    @ExceptionHandler(DuplicateInterestException.class)
    public ResponseEntity<Object> handleDuplicateInterest(DuplicateInterestException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of("error", 409, "message", ex.getMessage())
        );
    }
    @ExceptionHandler(UniqueSchoolViolationException.class)
    public ResponseEntity<Object> handleUniqueSchoolViolation(UniqueSchoolViolationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                Map.of(
                        "error", 403,
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(DisciplineInterestNotFoundException.class)
    public ResponseEntity<Object> handleDisciplineInterestNotFound(DisciplineInterestNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "code", 404,
                        "error", "Not Found",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(AvailabilityNotFoundException.class)
    public ResponseEntity<Object> handleAvailabilityNotFoundException(AvailabilityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        "code", 404,
                        "error", "Not Found",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(InvalidAvailabilityException.class)
    public ResponseEntity<Object> handleInvalidAvailabilityException(InvalidAvailabilityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "code", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(DuplicateAvailabilityException.class)
    public ResponseEntity<Object> handleDuplicateAvailability(DuplicateAvailabilityException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Map.of(
                        "error", 409,
                        "message", ex.getMessage()
                )
        );
    }
    @ExceptionHandler(InactiveEntityException.class)
    public ResponseEntity<Object> handleInactiveEntityException(InactiveEntityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                        "code", 400,
                        "error", "Bad Request",
                        "message", ex.getMessage()
                )
        );
    }
}
