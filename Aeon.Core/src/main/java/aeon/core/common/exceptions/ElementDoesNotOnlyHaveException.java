package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by RafaelT on 6/28/2016.
 */
public class ElementDoesNotOnlyHaveException extends RuntimeException {

    public ElementDoesNotOnlyHaveException(Collection<String> message) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotOnlyHaveException_ctor_DefaultMessage"), message));
    }
}
