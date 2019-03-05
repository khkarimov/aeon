package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is enabled.
 */
public class ElementIsEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotEnabledException} class.
     *
     * @param by The selector of the element that is not enabled.
     */
    public ElementIsEnabledException(IByWeb by) {
        super(String.format(Resources.getString("ElementIsEnabledException_ctor_SpecificMessage"), by.toString()));
    }
}
