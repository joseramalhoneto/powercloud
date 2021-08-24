package powercloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AreaInvalidException extends RuntimeException {
    public AreaInvalidException(String message) {
        super(message);
    }
}
