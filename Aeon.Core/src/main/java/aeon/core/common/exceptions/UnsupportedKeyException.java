package aeon.core.common.exceptions;

import aeon.core.common.KeyboardKey;
import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by RafaelT on 7/5/2016.
 */
public class UnsupportedKeyException extends RuntimeException {
    public UnsupportedKeyException(KeyboardKey key) {
        super(String.format(Locale.getDefault(), Resources.getString("UnsupportedKeyException_ctor_DefaultMessage"), key));
    }
}
