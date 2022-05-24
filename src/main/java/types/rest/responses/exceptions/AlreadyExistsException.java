package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RestException {
    public AlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
