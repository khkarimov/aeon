package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is not visible.
 */
public class ElementNotVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotVisibleException} class.
     *
     * @param by The selector of the element that is not visible.
     */
    public ElementNotVisibleException(IByWeb by) {
        super(String.format(Resources.getString("ElementNotVisibleException_ctor_SpecificMessage"), by.toString()));
    }
}
