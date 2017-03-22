package aeon.selenium;

import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;

/**
 * Created By PatrickA on 5/16/2016.
 */
public class SeleniumPlugin extends Plugin {
    /**
     * Constructor to be used By plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded By manager.
     *
     * @param wrapper
     */
    public SeleniumPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
