package echo.core.common.helpers;

import echo.core.common.exceptions.UnableToCreateURLException;

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
            throw new UnableToCreateURLException();
        }
    }
}
