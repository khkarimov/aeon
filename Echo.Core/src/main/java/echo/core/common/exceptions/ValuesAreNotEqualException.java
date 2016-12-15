package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by SebastianR on 6/29/2016.
 */
public class ValuesAreNotEqualException extends RuntimeException implements Serializable {

    public ValuesAreNotEqualException(String actualValue, String expectedValue) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreNotEqualException_ctor_DefaultMessage"), actualValue, expectedValue));
    }
}
