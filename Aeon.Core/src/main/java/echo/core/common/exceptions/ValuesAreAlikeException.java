package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class ValuesAreAlikeException extends RuntimeException {
    public ValuesAreAlikeException(String expected, String actual) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreAlikeException_ctor_DefaultMessage"), expected, actual));
    }
}
