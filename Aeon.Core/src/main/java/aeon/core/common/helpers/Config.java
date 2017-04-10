package aeon.core.common.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by josepe on 4/7/2017.
 */
public class Config {

    private static Config ourInstance = new Config();

    private boolean waitForAjaxWaiter;

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
        Properties properties = new Properties();
        InputStream in = null;

        try{
            String output = System.getProperty("user.dir");
            in = Config.class.getResourceAsStream("/config.properties");
            properties.load(in);
            String environmentValue = System.getenv("waitForAjaxWaiter");
            if(environmentValue == null)
                waitForAjaxWaiter = properties.getProperty("waitForAjaxWaiter", "true").equals("true")? true : false;
            else
                waitForAjaxWaiter = Boolean.valueOf(environmentValue);
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

    public boolean getWaitForAjaxWaiter() {
        return waitForAjaxWaiter;
    }

    public void setWaitForAjaxWaiter(boolean waitForAjaxWaiter) {
        this.waitForAjaxWaiter = waitForAjaxWaiter;
    }
}
