package com.ultimatesoftware.aeon.platform;

import com.ultimatesoftware.aeon.platform.factories.ISessionFactory;

/**
 * Aeon Platform.
 */
public class AeonPlatform {

    private AeonPlatform() {
        // Static classes should not be instantiated.
    }

    /**
     * Create a session factory.
     *
     * @return Session factory.
     */
    public static ISessionFactory getSessionFactory() {
        return DaggerAeonPlatformComponent.create().buildSessionFactory();
    }
}
