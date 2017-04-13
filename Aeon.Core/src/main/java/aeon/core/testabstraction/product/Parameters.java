package aeon.core.testabstraction.product;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by josepe on 4/7/2017.
 */
public class Parameters extends ParametersBase {
    private static Logger log = LogManager.getLogger(Parameters.class);
    public enum Keys implements IKeys{
        wait_for_ajax_response,
        default_timeout,
        prompt_user_for_continue_on_exception_decision,
        ensure_clean_environment,
        browser_type
    };
    public Parameters() {
        properties = new Properties();
        InputStream in = null;
        try{
            in = Parameters.class.getResourceAsStream("/aeon.properties");
            properties.load(in);
        } catch(IOException e){
             log.error("No aeon.properties found");
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

        try{
            in = Parameters.class.getResourceAsStream("/config.properties");
            if(in != null)
                properties.load(in);
            else
                log.info("No config file in use, using default values.");
        } catch(IOException e){
        } finally{
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        for(Keys key : Keys.values()){
            String environmentValue = System.getenv("aeon." + key.toString());
            if(environmentValue != null)
                properties.setProperty(key.toString(), environmentValue);
        }
    }
}
