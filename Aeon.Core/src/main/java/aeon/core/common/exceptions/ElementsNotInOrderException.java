package aeon.core.common.exceptions;

import aeon.core.common.CompareType;
import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 6/17/2016.
 */
public class ElementsNotInOrderException extends RuntimeException {

    public ElementsNotInOrderException(CompareType compareType) {
        super(String.format(Locale.getDefault(), Resources.getString("ElementsNotInOrderExceptionException_ctor_DefaultMessage"), compareType.toString()));
    }
}
