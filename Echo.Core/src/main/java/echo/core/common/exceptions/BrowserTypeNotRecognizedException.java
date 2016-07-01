package echo.core.common.exceptions;

import echo.core.common.Resources;

/**
 * Created by RafaelT on 7/1/2016.
 */
public class BrowserTypeNotRecognizedException extends RuntimeException {
    public BrowserTypeNotRecognizedException() {
        super(Resources.getString("BrowserTypeNotRecognizedException_ctor_DefaultMessage"));
    }
}