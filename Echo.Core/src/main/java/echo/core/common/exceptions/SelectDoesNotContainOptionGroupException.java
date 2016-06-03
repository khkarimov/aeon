package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 6/1/2016.
 */
public class SelectDoesNotContainOptionGroupException extends RuntimeException {
    public SelectDoesNotContainOptionGroupException () {
        super(Resources.getString("SelectDoesNotContainOptionGroupException_ctor_DefaultMessage"));
    }
}
