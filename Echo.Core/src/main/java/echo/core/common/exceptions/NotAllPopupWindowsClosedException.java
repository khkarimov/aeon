package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

public class NotAllPopupWindowsClosedException extends RuntimeException implements Serializable {

    /**
     * Initializes a new instance of the {@link NotAllPopupWindowsClosedException} class.
     */
    public NotAllPopupWindowsClosedException() {
        super(Resources.getString("NotAllPopupWindowsClosedException_ctor_DefaultMessage"));
    }
}
