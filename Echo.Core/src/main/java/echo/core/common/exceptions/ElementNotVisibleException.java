package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by Administrator on 6/29/2016.
 */
public class ElementNotVisibleException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the <see cref="ElementNotVisibleException"/> class.
     */
    public ElementNotVisibleException() {
        super(Resources.getString("ElementNotVisibleException_ctor_DefaultMessage"));
    }
}
