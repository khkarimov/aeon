package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by Administrator on 6/29/2016.
 */
public class ElementNotSelectedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementNotSelectedException} class.
     */
    public ElementNotSelectedException() {
        super(Resources.getString("ElementNotSelectedException_ctor_DefaultMessage"));
    }
}
