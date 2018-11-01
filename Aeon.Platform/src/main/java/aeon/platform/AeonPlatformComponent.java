package aeon.platform;

import aeon.platform.factories.SessionFactory;
import com.rabbitmq.client.Channel;
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
     * Builds a Channel.
     * @return Channel
     */
    Channel buildChannel();
}
