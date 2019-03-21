package com.ultimatesoftware.aeon.platform.http;

import com.ultimatesoftware.aeon.platform.DaggerAeonPlatformComponent;
import com.ultimatesoftware.aeon.platform.factories.SessionFactory;
import com.ultimatesoftware.aeon.platform.http.threads.ThreadFactory;
import com.ultimatesoftware.aeon.platform.session.ISession;
import io.dropwizard.Configuration;
import org.bson.types.ObjectId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * Gets the session table.
     *
     * @return Concurrent hash map
     */
    Map<ObjectId, ISession> getSessionTable() {
        return new ConcurrentHashMap<>();
    }
}
