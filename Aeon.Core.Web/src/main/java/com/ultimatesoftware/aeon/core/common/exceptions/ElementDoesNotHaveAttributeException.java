package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;

import java.util.Locale;

/**
 * Class that handles the element does not have attribute exception.
 */
public class ElementDoesNotHaveAttributeException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementDoesNotHaveAttributeException} class.
     *
     * @param element       The element that does not have the attribute.
     * @param attributeName The name of the attribute that is not on the element.
     */
    public ElementDoesNotHaveAttributeException(WebControl element, String attributeName) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementDoesNotHaveAttributeException_ctor_DefaultMessage"), element.getSelector(), attributeName));
    }
}
