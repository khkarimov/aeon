package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by SebastianR on 6/29/2016.
 */
public class ValuesAreNotAlikeException extends RuntimeException implements Serializable {

    public ValuesAreNotAlikeException(String actual, String expected) {
        super(String.format(Locale.getDefault(), Resources.getString("ValuesAreNotAlikeException_ctor_DefaultMessage"), expected, actual));
    }
}
