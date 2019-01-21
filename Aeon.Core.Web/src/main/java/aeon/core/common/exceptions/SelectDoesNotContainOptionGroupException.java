package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * The class to handle the exception thrown when select does not contain option group.
 */
public class SelectDoesNotContainOptionGroupException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link SelectDoesNotContainOptionGroupException} class.
     */
    public SelectDoesNotContainOptionGroupException() {
        super(Resources.getString("SelectDoesNotContainOptionGroupException_ctor_DefaultMessage"));
    }
}
