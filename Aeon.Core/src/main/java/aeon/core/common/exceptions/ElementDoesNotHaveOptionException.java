package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 6/1/2016.
 */
public class ElementDoesNotHaveOptionException extends RuntimeException {
    public ElementDoesNotHaveOptionException(String message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveOptionException_ctor_DefaultMessage"), message));
    }
}
