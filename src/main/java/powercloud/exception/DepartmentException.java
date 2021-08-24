package powercloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class DepartmentException extends RuntimeException {
    public DepartmentException(String message) {
        super(message);
    }
}
