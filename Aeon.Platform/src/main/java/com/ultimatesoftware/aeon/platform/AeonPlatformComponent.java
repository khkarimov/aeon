package com.ultimatesoftware.aeon.platform;

import com.ultimatesoftware.aeon.platform.factories.SessionFactory;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Creates injector for the Aeon Platform Module.
 */
@Singleton
@Component(modules = AeonPlatformModule.class)
public interface AeonPlatformComponent {

    /**
     * Builds a Session Factory.
     *
     * @return Session Factory
     */
    SessionFactory buildSessionFactory();
}
