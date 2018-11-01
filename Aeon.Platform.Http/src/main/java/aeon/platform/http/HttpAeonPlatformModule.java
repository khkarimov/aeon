package aeon.platform.http;

import aeon.platform.http.exceptions.ChannelCreationException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dagger.Module;
import dagger.Provides;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Module to provide RabbitMQ Channel.
 */
@Module
class HttpAeonPlatformModule {

    private static Logger log = LogManager.getLogger(HttpAeonPlatformModule.class);

    /**
     * Provides the Channel.
     *
     * @return Channel
     */
    @Provides
    @Singleton
    Channel provideChannel() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            return connection.createChannel();
        } catch (IOException | TimeoutException e) {
            log.error("Could not create connection to RabbitMQ.", e);
            throw new ChannelCreationException(e);
        }
    }
}
