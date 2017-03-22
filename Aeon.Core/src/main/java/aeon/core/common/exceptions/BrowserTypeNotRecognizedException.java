package aeon.core.common.exceptions;

import aeon.core.common.Resources;

/**
 * Created By RafaelT on 7/1/2016.
 */
public class BrowserTypeNotRecognizedException extends RuntimeException {
    public BrowserTypeNotRecognizedException() {
        super(Resources.getString("BrowserTypeNotRecognizedException_ctor_DefaultMessage"));
    }
}
