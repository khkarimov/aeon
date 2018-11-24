package aeon.cloud.services;

import aeon.cloud.AeonCloudException;
import aeon.cloud.repositories.DeploymentTimeRepository;
import aeon.cloud.repositories.RunnerRepository;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class RunnerServiceFactoryTests {

    private RunnerServiceFactory runnerServiceFactory;

    @Mock
    private RunnerRepository runnerRepository;

    @Mock
    private DeploymentTimeRepository deploymentTimeRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private CloudFoundryOperationsFactory cloudFoundryOperationsFactory;

    @Mock
    private DefaultCloudFoundryOperations cloudFoundryOperations;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        runnerServiceFactory = new RunnerServiceFactory(
                this.runnerRepository,
                this.deploymentTimeRepository,
                this.notificationService,
                this.cloudFoundryOperationsFactory);
    }

    @Test
    public void createRunnerServicePCFWithNoCredentialsTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("organization", "organization");
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Please provide apiHost, username, " +
                "password, organization and space when working with PCF.");

        // Act
        runnerServiceFactory.createRunnerService(credentials);

        // Assert
    }

    @Test
    public void createRunnerServicePCFWithMissingApiHostTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "username");
        credentials.put("password", "password");
        credentials.put("organization", "organization");
        credentials.put("space", "space");
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Please provide apiHost, username, " +
                "password, organization and space when working with PCF.");

        // Act
        runnerServiceFactory.createRunnerService(credentials);

        // Assert
    }

    @Test
    public void createRunnerServicePCFWithMissingUsernameTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("apiHost", "apiHost");
        credentials.put("password", "password");
        credentials.put("organization", "organization");
        credentials.put("space", "space");
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Please provide apiHost, username, " +
                "password, organization and space when working with PCF.");

        // Act
        runnerServiceFactory.createRunnerService(credentials);

        // Assert
    }

    @Test
    public void createRunnerServicePCFWithMissingPasswordTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("apiHost", "apiHost");
        credentials.put("username", "username");
        credentials.put("organization", "organization");
        credentials.put("space", "space");
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Please provide apiHost, username, " +
                "password, organization and space when working with PCF.");

        // Act
        runnerServiceFactory.createRunnerService(credentials);

        // Assert
    }

    @Test
    public void createRunnerServicePCFWithMissingSpaceTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("apiHost", "apiHost");
        credentials.put("username", "username");
        credentials.put("password", "password");
        credentials.put("organization", "organization");
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Please provide apiHost, username, " +
                "password, organization and space when working with PCF.");

        // Act
        runnerServiceFactory.createRunnerService(credentials);

        // Assert
    }

    @Test
    public void createRunnerServicePCFTest() {

        // Arrange
        Map<String, String> credentials = new HashMap<>();
        credentials.put("apiHost", "apiHost");
        credentials.put("username", "username");
        credentials.put("password", "password");
        credentials.put("organization", "organization");
        credentials.put("space", "space");

        // Act
        IRunnerService runnerService = runnerServiceFactory.createRunnerService(credentials);
        assertEquals(PCFRunnerService.class, runnerService.getClass());

        // Assert
        verify(this.cloudFoundryOperationsFactory, times(1))
                .getCloudFoundryOperations(
                        "apiHost",
                        "username",
                        "password",
                        "organization",
                        "space");
    }

    @Test
    public void createRunnerServiceNoneTest() {

        // Arrange
        thrown.expect(AeonCloudException.class);
        thrown.expectMessage("Could not detect the desired deployment platform based on the credentials");

        // Act
        runnerServiceFactory.createRunnerService(new HashMap<>());

        // Assert
    }
}
