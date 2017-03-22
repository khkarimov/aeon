package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created By RafaelT on 6/2/2016.
 */
public class ElementHasOptionException extends RuntimeException {
    public ElementHasOptionException(String option) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementHasOptionException_ctor_DefaultMessage"), option));
    }
}
