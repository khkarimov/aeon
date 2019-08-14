package com.ultimatesoftware.aeon.platform.factories;

import com.ultimatesoftware.aeon.platform.session.ISession;

import java.io.IOException;
import java.util.Map;

/**
 * The session factory interface.
 */
public interface ISessionFactory {

    /**
     * Creates a new session.
     *
     * @param settings Settings
     * @return Session
     * @throws IllegalAccessException Thrown if configuration keys could not be accessed.
     * @throws IOException            Thrown if configuration files could not be read.
     * @throws InstantiationException Thrown if the driver could not be instantiated.
     */
    ISession getSession(Map settings) throws IllegalAccessException, IOException, InstantiationException;
}
