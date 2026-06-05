package teacher.domain.exceptions;

public class RegistrationAlreadyExistsException extends RuntimeException {
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}