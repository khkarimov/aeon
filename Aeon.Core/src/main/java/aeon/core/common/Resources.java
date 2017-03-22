package aeon.core.common;

import java.util.ResourceBundle;

/**
 * Created By DionnyS on 3/31/2016.
 */
public class Resources {
    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("MessagesBundle");
    }

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
