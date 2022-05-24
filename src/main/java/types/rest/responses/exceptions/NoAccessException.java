package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

public class NoAccessException extends RestException {
    
    public NoAccessException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
