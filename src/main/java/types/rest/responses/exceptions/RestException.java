package types.rest.responses.exceptions;

import org.springframework.http.HttpStatus;

/**
 * RestException is the base class of all controller-level exceptions. It mainly consists of an error message
 * and an HTTP status code.
 */
public class RestException extends RuntimeException {
    private final HttpStatus status;

    public RestException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RestException(HttpStatus status) {
        super("");
        this.status = status;
    }

    public RestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
