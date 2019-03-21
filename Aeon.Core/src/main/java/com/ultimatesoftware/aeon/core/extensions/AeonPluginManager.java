package com.ultimatesoftware.aeon.core.extensions;

import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;

/**
 * The Aeon plugin manager.
 * <p>
 * Specializes the default plugin manager for Aeon's needs.
 */
public class AeonPluginManager extends DefaultPluginManager {
    private ISessionIdProvider sessionIdProvider;

    /**
     * Constructor for specifying the session ID provider to use.
     *
     * @param sessionIdProvider The session ID provider to use.
     */
    public AeonPluginManager(ISessionIdProvider sessionIdProvider) {
        this.sessionIdProvider = sessionIdProvider;
        initialize();
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new AeonExtensionFactory(this.sessionIdProvider);
    }
}
