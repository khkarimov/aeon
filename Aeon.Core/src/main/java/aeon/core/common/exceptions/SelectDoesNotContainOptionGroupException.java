package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By RafaelT on 6/1/2016.
 */
public class SelectDoesNotContainOptionGroupException extends RuntimeException {
    public SelectDoesNotContainOptionGroupException() {
        super(Resources.getString("SelectDoesNotContainOptionGroupException_ctor_DefaultMessage"));
    }
}
