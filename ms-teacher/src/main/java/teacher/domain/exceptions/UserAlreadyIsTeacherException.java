package teacher.domain.exceptions;

public class UserAlreadyIsTeacherException extends RuntimeException {
    public UserAlreadyIsTeacherException(String message) {
        super(message);
    }
}
