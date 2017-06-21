package aeon.core.common.exceptions;
import aeon.core.common.Resources;

public class WebUsingMobileCommandException extends RuntimeException {

    public WebUsingMobileCommandException() {
        super(Resources.getString("WebUsingMobileCommandException_ctor_DefaultMessage"));
    }
}