package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

/**
 * NotFoundException is an exception that indicates that a requested
 * resource was not found in the server.
 */
public class NotFoundException extends RestException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
