package aeon.platform.http;

import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.factories.SessionFactory;
import com.rabbitmq.client.*;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Launches browser.
 */
@SpringBootApplication
public class AeonApp {

    //private static final String QUEUE_NAME = "AeonApp";

    /**
     * Gets the session factory.
     * @return Session factory
     */
    @Bean
    public SessionFactory getSessionFactory() {
        return DaggerAeonPlatformComponent.create().buildSessionFactory();
    }

    /**
     * Gets the thread factory.
     * @return Thread factory
     */
    @Bean
    public ThreadFactory getThreadFactory() {
        return new ThreadFactory();
    }

    /**
     * Main method of the application.
     * @param args Command line arguments
     * @throws IOException Throws an exception if an IO error occurs
     * @throws TimeoutException Throws an exception if a timeout error occurs
     */
    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        System.out.println("Waiting for messages...");
//
//        Consumer consumer = new DefaultConsumer(channel) {
//            @Override
//            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
//                String message = new String(body, StandardCharsets.UTF_8);
//                JSONObject messageJson = new JSONObject(message);
//
//                System.out.println("\nReceived message: " + messageJson);
//            }
//        };
//
//        channel.basicConsume(QUEUE_NAME, true, consumer);

        SpringApplication.run(AeonApp.class, args);
    }
}
