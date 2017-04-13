package aeon.core.testabstraction.product;


import java.util.Properties;

/**
 * Created by josepe on 4/12/2017.
 */
public abstract class ParametersBase {
    public interface IKeys{

    }
    public Properties properties;

    public void setBoolean(IKeys key, boolean value){
        set(key.toString(), Boolean.toString(value));
    }

    public void setString(IKeys key, String value){
        set(key.toString(), value);
    }

    public void setDouble(IKeys key, double value){
        set(key.toString(), Double.toString(value));
    }

    private void set(String key, String value){
        properties.setProperty(key, value);
    }

    public boolean getBoolean(IKeys key, boolean defaultValue){
        return Boolean.valueOf(get(key.toString(), Boolean.toString(defaultValue)));
    }

    public double getDouble(IKeys key, double defaultValue){
        return Double.parseDouble(get(key.toString(), Double.toString(defaultValue)));
    }

    public String getString(IKeys key, String defaultValue){
        return (get(key.toString(), defaultValue));
    }

    private String get(String key, String defaultValue){
        return properties.getProperty(key, defaultValue);
    }
}
