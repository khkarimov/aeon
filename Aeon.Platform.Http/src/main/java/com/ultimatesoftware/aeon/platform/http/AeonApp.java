package com.ultimatesoftware.aeon.platform.http;

import com.codahale.metrics.health.HealthCheck;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.platform.http.controllers.HttpSessionController;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
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
                new SubstitutingSourceProvider(new ResourceConfigurationSourceProvider(),
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

        environment.healthChecks().register("application", new HealthCheck() {
            @Override
            protected Result check() {
                return Result.healthy();
            }
        });

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
