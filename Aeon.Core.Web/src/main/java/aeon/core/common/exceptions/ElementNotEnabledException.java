package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IByWeb;

import java.io.Serializable;

/**
 * Class that handles the exception thrown when the element is not enabled.
 */
public class ElementNotEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotEnabledException} class.
     *
     * @param by The selector of the element that is not enabled.
     */
    public ElementNotEnabledException(IByWeb by) {
        super(String.format(Resources.getString("ElementNotEnabledException_ctor_SpecificMessage"), by.toString()));
    }
}
