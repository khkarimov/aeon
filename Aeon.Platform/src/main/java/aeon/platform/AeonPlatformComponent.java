package aeon.platform;

import aeon.platform.services.CommandService;
import aeon.platform.services.SessionFactory;
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
     * @return Session Factory
     */
    SessionFactory buildSessionFactory();

    /**
     * Builds a Command Service.
     * @return Command Service
     */
    CommandService buildCommandService();
}
