package com.ultimatesoftware.aeon.core.common.helpers;

import com.ultimatesoftware.aeon.core.common.exceptions.UnableToCreateURLException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to represent a url utility.
 */
public class URLUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private URLUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Given a string URL, it creates a URL object.
     *
     * @param url the input url as a string.
     * @return the new URL object.
     */
    public static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new UnableToCreateURLException(url);
        }
    }
}
