package com.ultimatesoftware.aeon.core.common;

import java.util.ResourceBundle;

/**
 * Resources class for reading message strings.
 */
public class Resources {

    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("MessagesBundle");
    }

    private Resources() {
        // Static classes should not be instantiated.
    }

    /**
     * Gets the string if the bundle contains the input key.
     *
     * @param key the input string.
     * @return the string of the bundle at the key.
     */
    public static String getString(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else {
            return key;
        }
    }
}
