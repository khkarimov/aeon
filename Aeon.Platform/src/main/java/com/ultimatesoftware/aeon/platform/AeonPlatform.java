package com.ultimatesoftware.aeon.platform;

import com.ultimatesoftware.aeon.platform.factories.ISessionFactory;

/**
 * Aeon Platform.
 */
public class AeonPlatform {

    /**
     * A private constructor to hide the implicit public constructor.
     */
    private AeonPlatform() {
        throw new IllegalStateException("Incorrect initialization of the AeonPlatform object!");
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
