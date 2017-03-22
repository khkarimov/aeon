package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Created By Administrator on 6/29/2016.
 */
public class ElementNotVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotVisibleException} class.
     */
    public ElementNotVisibleException() {
        super(Resources.getString("ElementNotVisibleException_ctor_DefaultMessage"));
    }
}
