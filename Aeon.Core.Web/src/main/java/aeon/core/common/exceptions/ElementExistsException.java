package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element exists.
 */
public class ElementExistsException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementExistsException} class.
     */
    public ElementExistsException() {
        super(Resources.getString("ElementExistsException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link ElementExistsException} class.
     *
     * @param by The selector of the element that exists.
     */
    public ElementExistsException(IByWeb by) {
        super(String.format(Resources.getString("ElementExistsException_ctor_SpecificMessage"), by.toString()));
    }
}
