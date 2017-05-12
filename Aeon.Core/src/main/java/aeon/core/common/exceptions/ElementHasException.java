package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementHasException extends RuntimeException {

    public ElementHasException(String message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementHasException_ctor_DefaultMessage"), message));
    }
}
