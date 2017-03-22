package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementDoesNotHaveException extends RuntimeException {
    public ElementDoesNotHaveException(String value) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveException_ctor_DefaultMessage")));
    }
}
