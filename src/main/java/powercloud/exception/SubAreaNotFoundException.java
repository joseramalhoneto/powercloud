package powercloud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubAreaNotFoundException extends RuntimeException{
    public SubAreaNotFoundException(String message) {
        super(message);
    }
}
