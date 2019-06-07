package com.ultimatesoftware.aeon.core.common.exceptions;

import com.ultimatesoftware.aeon.core.common.Resources;
import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByXPath;

import java.io.Serializable;

/**
 * The exception that is thrown when an IBy element does not exist.
 */
public class NoSuchElementException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link NoSuchElementException} class.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     * @param by             The unfound IBy element causing the exception.
     */
    public NoSuchElementException(Exception innerException, IBy by) {
        super(String.format(Resources.getString("NoSuchElementException_ctor_SpecificMessage"), by.toString()), innerException);
    }

    /**
     * Initializes a new instance of the {@link NoSuchElementException} class for an XPath selector.
     *
     * @param innerException The exception that is the cause of the current exception, or a null reference (Nothing in Visual Basic) if no inner exception is specified.
     * @param by             The not found IByXPath element causing the exception.
     */
    public NoSuchElementException(Exception innerException, IByXPath by) {
        super(String.format(Resources.getString("NoSuchElementException_ctor_IByXPath_SpecificMessage"), by.toString()), innerException);
    }
}
