package cz.muni.fi.pa165.referenceManager.rest.exceptions;

/**
 * Exception converted by MyExceptionHandler to NOT_FOUND HTTP status.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {}

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
