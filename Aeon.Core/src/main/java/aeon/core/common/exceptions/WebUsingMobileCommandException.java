package aeon.core.common.exceptions;
import aeon.core.common.Resources;

/**
 * Class to handle exceptions thrown by web using mobile command.
 */
public class WebUsingMobileCommandException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link WebUsingMobileCommandException} class.
     */
    public WebUsingMobileCommandException() {
        super(Resources.getString("WebUsingMobileCommandException_ctor_DefaultMessage"));
    }
}
