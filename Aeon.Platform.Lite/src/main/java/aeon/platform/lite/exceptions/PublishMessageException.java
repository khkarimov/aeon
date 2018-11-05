package aeon.platform.lite.exceptions;

/**
 * The exception that is thrown to indicate a message could not be published.
 */
public class PublishMessageException extends RuntimeException {

    /**
     * Initializes new instance of the {@link PublishMessageException} class with the specified exception.
     *
     * @param e Exception
     */
    public PublishMessageException(Exception e) {
        super(e);
    }
}
