package common;

import java.util.ResourceBundle;

/**
 * Created by DionnyS on 3/31/2016.
 */
public class Resources {
    private static final ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("MessagesBundle");
    }

    public static String getString(String key) {
        return bundle.getString(key);
    }
}
