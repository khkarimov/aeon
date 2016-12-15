package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by Administrator on 6/29/2016.
 */
public class ElementIsSelectedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsSelectedException} class.
     */
    public ElementIsSelectedException() {
        super(Resources.getString("ElementIsSelectedException_ctor_DefaultMessage"));
    }
}
