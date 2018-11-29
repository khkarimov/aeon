package aeon.cloud;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception used for errors when managing runners.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AeonCloudException extends RuntimeException {

    /**
     * Message for the error.
     *
     * @param message The message.
     */
    public AeonCloudException(String message) {
        super(message);
    }
}
