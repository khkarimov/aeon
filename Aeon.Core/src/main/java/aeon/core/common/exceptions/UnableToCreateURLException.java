package aeon.core.common.exceptions;

import aeon.core.common.Resources;

import java.util.Locale;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class UnableToCreateURLException extends RuntimeException {
    public UnableToCreateURLException(String url){
        super(String.format(Locale.getDefault(), Resources.getString("UnableToCreateURLException_ctor_DefaultMessage"), url));
    }
}
