package aeon.platform.http;

import com.rabbitmq.client.Channel;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Creates injector for the Http Aeon Platform Module.
 */
@Singleton
@Component(modules = HttpAeonPlatformModule.class)
public interface HttpAeonPlatformComponent {

    /**
     * Builds a Channel.
     *
     * @return Channel
     */
    Channel buildChannel();
}
