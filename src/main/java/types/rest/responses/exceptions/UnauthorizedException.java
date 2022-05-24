package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RestException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
