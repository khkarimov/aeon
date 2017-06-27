package aeon.core.common;

import java.util.ResourceBundle;

/**
 * Created by DionnyS on 3/31/2016.
 */
public class Resources {

    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("MessagesBundle");
    }

    /**
     * Gets the string if the bundle contains the input key.
     * @param key the input string.
     * @return the string of the bundle at the key.
     */
    public static String getString(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else {
            return key; //"No message in MessagesBundle in Aeon.Core.Resources";
        }
        //return bundle.getString(key);
        //return key;
    }
}
