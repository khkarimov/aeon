package com.ultimatesoftware.aeon.extensions.rnr;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * Plugin class for RnR Plugin.
 */
public class RnrPlugin extends Plugin {

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public RnrPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
