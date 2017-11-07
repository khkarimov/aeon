package aeon.selenium.appium;

import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

/**
 * Class necessary for PF4J.
 */
public class AppiumPlugin extends Plugin {
    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public AppiumPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
