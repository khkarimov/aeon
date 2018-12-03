package aeon.platform.http;

import aeon.core.testabstraction.product.Aeon;
import aeon.platform.http.controllers.HttpSessionController;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Launches browser.
 */
public class AeonApp extends Application<AeonAppConfiguration> {

    @Override
    public void initialize(Bootstrap<AeonAppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)));
    }

    @Override
    public void run(AeonAppConfiguration configuration, Environment environment) {
        final HttpSessionController controller = new HttpSessionController(
                configuration.getSessionFactory(),
                configuration.getThreadFactory(),
                configuration.getSessionTable()
        );

        environment.jersey().register(controller);

        Aeon.setSessionIdProvider(new HttpSessionIdProvider());
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
