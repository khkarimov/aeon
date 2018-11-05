package aeon.platform.lite;

import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.factories.SessionFactory;
import aeon.platform.lite.threads.ThreadFactory;
import io.dropwizard.Configuration;

/**
 * Aeon App Configuration.
 */
class AeonAppConfiguration extends Configuration {

    /**
     * Gets the session factory.
     *
     * @return Session factory
     */
    SessionFactory getSessionFactory() {
        return DaggerAeonPlatformComponent.create().buildSessionFactory();
    }

    /**
     * Gets the thread factory.
     *
     * @return Thread factory
     */
    ThreadFactory getThreadFactory() {
        return new ThreadFactory();
    }
}
