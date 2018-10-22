package aeon.platform.http;

import aeon.platform.DaggerAeonPlatformComponent;
import aeon.platform.factories.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Launches browser.
 */
@SpringBootApplication
public class AeonApp {

    /**
     * Gets the session service.
     * @return Sessions service
     */
    @Bean
    public SessionFactory getSessionFactory() {
        return DaggerAeonPlatformComponent.create().buildSessionFactory();
    }

    /**
     * Main method of the application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AeonApp.class, args);
    }
}
