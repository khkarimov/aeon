package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;

/**
 * Created by RafaelT on 5/31/2016.
 */
public class ElementNotEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotEnabledException} class.
     */
    public ElementNotEnabledException() {
        super(Resources.getString("ElementNotEnabledException_ctor_DefaultMessage"));
    }
}
