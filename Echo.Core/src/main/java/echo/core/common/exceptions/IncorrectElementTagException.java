package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class IncorrectElementTagException extends RuntimeException {
    public IncorrectElementTagException(String expectedTagName, String actualTagName) {
        super(String.format(Resources.getString("IncorrectElementTagException_ctor_DefaultMessage"), actualTagName, expectedTagName));
    }
}
