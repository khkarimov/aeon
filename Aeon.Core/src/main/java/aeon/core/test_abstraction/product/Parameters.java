package aeon.core.test_abstraction.product;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class Parameters extends HashMap<String, Object> {
    public <T> T getParameter(Class<T> cls, String name) {
        return cls.cast(get(name));
    }

    public boolean getBoolean(String name) {
        return (boolean) get(name);
    }

    public String getString(String name) {
        return get(name).toString();
    }

    public int getInt(String name) {
        return (int) get(name);
    }

    public void load(ResourceBundle bundle) {
        Enumeration<String> keys = bundle.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            put(key, bundle.getObject(key));
        }
    }

    public void load(HashMap<String, Object> settings) {
        putAll(settings);
    }
}
