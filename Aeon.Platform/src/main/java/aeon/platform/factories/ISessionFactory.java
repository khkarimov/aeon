package aeon.platform.factories;

import aeon.platform.session.ISession;

import java.util.Map;

/**
 * The session factory interface.
 */
public interface ISessionFactory {

    /**
     * Creates a new session.
     * @param settings Settings
     * @return Session
     * @throws Exception Throws an exception if an error occurs
     */
    ISession getSession(Map settings) throws Exception;
}
