package aeon.platform;

import aeon.platform.factories.ISessionFactory;

/**
 * Aeon Platform.
 */
public class AeonPlatform {

    /**
     * Create a session factory.
     *
     * @return Session factory.
     */
    public static ISessionFactory getSessionFactory() {
        return DaggerAeonPlatformComponent.create().buildSessionFactory();
    }
}
