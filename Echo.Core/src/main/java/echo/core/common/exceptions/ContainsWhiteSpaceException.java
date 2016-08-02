package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by SebastianR on 6/28/2016.
 */
public class ContainsWhiteSpaceException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link ContainsWhiteSpaceException} class.
     */
    public ContainsWhiteSpaceException() {
        super(Resources.getString("ContainsWhiteSpaceException_ctor_DefaultMessage"));
    }
}
