package aeon.platform.lite.exceptions;

/**
 * The exception that is thrown to indicate a RabbitMQ channel was not created.
 */
public class ChannelCreationException extends RuntimeException {

    /**
     * Initializes new instance of the {@link ChannelCreationException} class with the specified exception.
     *
     * @param e Exception
     */
    public ChannelCreationException(Exception e) {
        super(e);
    }
}
