package com.ultimatesoftware.aeon.cloud.services;

import com.ultimatesoftware.aeon.cloud.AeonCloudException;
import com.ultimatesoftware.aeon.cloud.repositories.DeploymentTimeRepository;
import com.ultimatesoftware.aeon.cloud.repositories.RunnerRepository;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Factory for creating runner services.
 */
@Service
public class RunnerServiceFactory {

    private final RunnerRepository runnerRepository;
    private final DeploymentTimeRepository deploymentTimeRepository;
    private final NotificationService notificationService;
    private final CloudFoundryOperationsFactory cloudFoundryOperationsFactory;

    /**
     * Constructor.
     *
     * @param runnerRepository              The runner repository to use.
     * @param deploymentTimeRepository      The deploymentTime repository to use.
     * @param notificationService           The notification service to use.
     * @param cloudFoundryOperationsFactory CloudFoundry operations.
     */
    @Autowired
    public RunnerServiceFactory(RunnerRepository runnerRepository,
                                DeploymentTimeRepository deploymentTimeRepository,
                                NotificationService notificationService,
                                CloudFoundryOperationsFactory cloudFoundryOperationsFactory) {
        this.runnerRepository = runnerRepository;
        this.deploymentTimeRepository = deploymentTimeRepository;
        this.notificationService = notificationService;
        this.cloudFoundryOperationsFactory = cloudFoundryOperationsFactory;
    }

    /**
     * Creates a runner service depending on the provided credentials.
     *
     * @param credentials Credentials needed for the deployment service.
     * @return The runner service.
     */
    public IRunnerService createRunnerService(Map<String, String> credentials) {

        if (credentials.containsKey("organization")) {
            if (!credentials.containsKey("apiHost")
                    || !credentials.containsKey("username")
                    || !credentials.containsKey("password")
                    || !credentials.containsKey("space")) {
                throw new AeonCloudException("Please provide apiHost, username, " +
                        "password, organization and space when working with PCF.");
            }

            CloudFoundryOperations cloudFoundryOperations
                    = cloudFoundryOperationsFactory.getCloudFoundryOperations(
                    credentials.get("apiHost"),
                    credentials.get("username"),
                    credentials.get("password"),
                    credentials.get("organization"),
                    credentials.get("space")
            );

            return new PCFRunnerService(
                    runnerRepository,
                    deploymentTimeRepository,
                    notificationService,
                    cloudFoundryOperations);
        }

        throw new AeonCloudException(
                "Could not detect the desired deployment platform based on the credentials"
        );
    }
}
