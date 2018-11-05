package aeon.platform.lite;

import aeon.platform.lite.controllers.HttpSessionController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Launches browser.
 */
public class AeonApp extends Application<AeonAppConfiguration> {

    @Override
    public void run(AeonAppConfiguration configuration, Environment environment) {
        final HttpSessionController controller = new HttpSessionController(
                configuration.getSessionFactory(),
                configuration.getThreadFactory()
        );

        environment.jersey().register(controller);
    }

    /**
     * Main method of the application.
     *
     * @param args Command line arguments
     * @throws Exception Throws an exception if an error occurs
     */
    public static void main(String[] args) throws Exception {
        new AeonApp().run(args);
    }
}
