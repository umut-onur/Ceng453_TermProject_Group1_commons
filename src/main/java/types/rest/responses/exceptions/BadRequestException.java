package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

/**
 * BadRequestException is an exception that indicates that client has sent
 * erroneous input and must fix them for requested process to execute correctly.
 */
public class BadRequestException extends RestException {
    public BadRequestException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
