package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created By SebastianR on 8/9/2016.
 */
public class WindowExistsException extends RuntimeException implements Serializable {
    public WindowExistsException(String window) {
        super(String.format(Locale.getDefault(), Resources.getString("WindowExistsException_ctor_DefaultMessage"), window));
    }
}
