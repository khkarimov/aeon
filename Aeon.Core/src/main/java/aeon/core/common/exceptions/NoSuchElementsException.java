package aeon.core.common.exceptions;

import aeon.core.common.Resources;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.ByJQuery;

import java.io.Serializable;

/**
 * The exception that is thrown when a ByJQuery element does not exist.
 */
public class NoSuchElementsException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     *
     * @param byJQuery The unfound ByJQuery element.
     */
    public NoSuchElementsException(ByJQuery byJQuery) {
        super(String.format(Resources.getString("NoSuchElementsException_ctor_JQuerry_SpecificMessage"), byJQuery.toString()));
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     * @param byJQuery The unfound ByJQuery element.
     */
    public NoSuchElementsException(Exception innerException, ByJQuery byJQuery) {
        super(String.format(Resources.getString("NoSuchElementsException_ctor_JQuerry_SpecificMessage"), byJQuery.toString()), innerException);
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     *
     * @param by The unfound IBy element.
     */
    public NoSuchElementsException(IBy by) {
        super(String.format(Resources.getString("NoSuchElementsException_ctor_IBy_SpecificMessage"), by.toString()));
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementsException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     * @param by The un-found IBy element.
     */
    public NoSuchElementsException(Exception innerException, IBy by) {
        super(String.format(Resources.getString("NoSuchElementsException_ctor_IBy_SpecificMessage"), by.toString()), innerException);
    }
}
