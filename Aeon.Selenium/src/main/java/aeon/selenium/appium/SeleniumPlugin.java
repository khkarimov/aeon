package aeon.selenium.appium;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;
/**
 * Created by PatrickA on 5/16/2016.
 */
public class SeleniumPlugin extends Plugin {
    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public SeleniumPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
