package powercloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class SubAreaInvalidException extends RuntimeException {
    public SubAreaInvalidException(String message) {
        super(message);
    }
}
