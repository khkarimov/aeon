package com.ultimatesoftware.aeon.extensions.slack;

import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * Plugin class for the Slack Plugin.
 */
public class SlackPlugin extends Plugin {

    /**
     * Constructor to be used by plugin manager for plugin instantiation.
     * Your plugins have to provide constructor with this exact signature to
     * be successfully loaded by manager.
     *
     * @param wrapper The plugin wrapper to be set.
     */
    public SlackPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }
}
