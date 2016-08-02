package echo.core.common.exceptions;

import echo.core.common.KeyboardKey;
import echo.core.common.Resources;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class UnsupportedKeyException extends RuntimeException {
    public UnsupportedKeyException(KeyboardKey key) {
        super(String.format(Resources.getString("UnsupportedKeyException_ctor_DefaultMessage"), key));
    }
}
