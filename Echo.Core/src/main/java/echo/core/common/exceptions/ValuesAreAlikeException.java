package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class ValuesAreAlikeException extends RuntimeException {
    public ValuesAreAlikeException(String expected, String actual) {
        super(String.format(Resources.getString("ValuesAreAlikeException_ctor_DefaultMessage"), expected, actual));
    }
}
