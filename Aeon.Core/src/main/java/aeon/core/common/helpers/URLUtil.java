package aeon.core.common.helpers;

import aeon.core.common.exceptions.UnableToCreateURLException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DionnyS on 4/21/2016.
 */
public class URLUtil {
    public static URL CreateURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new UnableToCreateURLException(url);
        }
    }
}
