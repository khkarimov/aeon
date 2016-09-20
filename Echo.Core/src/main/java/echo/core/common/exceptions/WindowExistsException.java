package echo.core.common.exceptions;

import echo.core.common.Resources;

import java.io.Serializable;

/**
 * Created by SebastianR on 8/9/2016.
 */
public class WindowExistsException extends RuntimeException implements Serializable {
    private String window;


    public WindowExistsException(String window) {
        super(String.format(Resources.getString("WindowExistsException_ctor_DefaultMessage"), window));
        this.window = window;
    }
}
