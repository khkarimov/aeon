package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Created By Administrator on 6/27/2016.
 */
public class ElementIsEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsEnabledException} class.
     */
    public ElementIsEnabledException() {
        super(Resources.getString("ElementIsEnabledException_ctor_DefaultMessage"));
    }
}
