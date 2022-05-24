package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

/**
 * InternalServerException is an exception that indicates an unexpected
 * error occurred.
 */
public class InternalServerException extends RestException {
    public InternalServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
