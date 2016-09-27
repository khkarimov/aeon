package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by SebastianR on 6/29/2016.
 */
public class ValuesAreNotAlikeException extends RuntimeException implements Serializable {

    public ValuesAreNotAlikeException(String actual, String expected) {
        super(String.format(Resources.getString("ValuesAreNotAlikeException_ctor_DefaultMessage"), actual, expected));
    }
}
