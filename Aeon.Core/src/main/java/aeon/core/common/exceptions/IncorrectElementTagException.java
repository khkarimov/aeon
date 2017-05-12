package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class IncorrectElementTagException extends RuntimeException {

    public IncorrectElementTagException(String expectedTagName, String actualTagName) {
        super(String.format(Locale.getDefault(), Resources.getString("IncorrectElementTagException_ctor_DefaultMessage"), actualTagName, expectedTagName));
    }
}
