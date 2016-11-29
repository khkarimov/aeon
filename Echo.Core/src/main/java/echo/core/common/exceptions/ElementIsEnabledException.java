package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by Administrator on 6/27/2016.
 */
public class ElementIsEnabledException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ElementIsEnabledException} class.
     */
    public ElementIsEnabledException() {
        super(Resources.getString("ElementEnabledException_ctor_DefaultMessage"));
    }
}
