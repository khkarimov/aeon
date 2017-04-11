package aeon.core.common.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by josepe on 4/7/2017.
 */
public class Config {

    private static Config ourInstance = new Config();
    private final Properties properties;

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        properties = new Properties();
        InputStream in = null;

        try{
            in = Config.class.getResourceAsStream("/config.properties");
            properties.load(in);
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void setBoolean(String key, boolean value){
        set(key, Boolean.toString(value));
    }

    public void set(String key, String value){
        properties.setProperty(key, value);
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return Boolean.valueOf(get(key, Boolean.toString(defaultValue)));
    }

    public String get(String key, String defaultValue){
        String environmentValue = System.getenv(key);
        if(environmentValue != null)
            return environmentValue;

        return properties.getProperty(key, defaultValue);
    }
}
