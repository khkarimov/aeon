package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is visible.
 */
public class ElementIsVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsVisibleException} class.
     */
    public ElementIsVisibleException() {
        super(Resources.getString("ElementIsVisibleException_ctor_DefaultMessage"));
    }

    /**
     * Initializes a new instance of the {@link ElementIsVisibleException} class.
     *
     * @param by The selector of the element that is visible.
     */
    public ElementIsVisibleException(IByWeb by) {
        super(String.format(Resources.getString("ElementIsVisibleException_ctor_DefaultMessage"), by.toString()));
    }
}
