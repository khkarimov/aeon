package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by SebastianR on 6/29/2016.
 */
public class ValuesAreNotEqualException extends RuntimeException implements Serializable {

    public ValuesAreNotEqualException(String value, String comparingValue) {
        super(Resources.getString("ValuesAreNotEqualException_ctor_DefaultMessage") + " " + value + " does not equal " + comparingValue);
    }

    public ValuesAreNotEqualException(String value, String expected, String attribute) {
        super(Resources.getString("ValuesAreNotEqualException_ctor_DefaultMessage") + " " + value + " " + expected + " " + attribute);
    }
}
