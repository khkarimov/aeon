package aeon.core.common.exceptions;

import aeon.core.common.CompareType;
import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Class that handles the exception thrown when the elements are not in order.
 */
public class ElementsNotInOrderException extends RuntimeException {

    /**
     * Initializes a new instance of the {@link ElementsNotInOrderException} class.
     * @param compareType the input CompareType.
     */
    public ElementsNotInOrderException(CompareType compareType) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementsNotInOrderExceptionException_ctor_DefaultMessage"), compareType.toString()));
    }
}
