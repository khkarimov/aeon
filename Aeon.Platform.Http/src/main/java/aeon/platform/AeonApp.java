package aeon.platform;

import aeon.platform.services.SessionFactory;
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
    public SessionFactory getSessionService() {
        AeonPlatformComponent aeonPlatformComponent = DaggerAeonPlatformComponent.create();
        return aeonPlatformComponent.buildSessionFactory();
    }

    /**
     * Main method of the application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AeonApp.class, args);
    }
}
