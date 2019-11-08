package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;

import java.util.Locale;

/**
 * Class that handles the element has attribute exception.
 */
public class ElementHasAttributeException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementHasAttributeException} class.
     *
     * @param element       The element that has the attribute.
     * @param attributeName The name of the attribute that is on the element.
     */
    public ElementHasAttributeException(WebControl element, String attributeName) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementHasAttributeException_ctor_DefaultMessage"), element.getSelector(), attributeName));
    }
}
