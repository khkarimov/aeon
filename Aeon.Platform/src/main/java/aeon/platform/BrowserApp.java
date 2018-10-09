package aeon.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launches browser.
 */
@SpringBootApplication
public class BrowserApp {

    /**
     * Main method of the application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BrowserApp.class, args);
    }
}
