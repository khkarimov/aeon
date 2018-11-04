package aeon.cloud.controllers;

import aeon.cloud.models.CreateRunnersPayload;
import aeon.cloud.models.DeleteRunnersPayload;
import aeon.cloud.models.PCF;
import aeon.cloud.models.Runner;
import aeon.cloud.repositories.RunnerRepository;
import aeon.cloud.services.CloudFoundryOperationsFactory;
import aeon.cloud.services.RunnerService;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class RunnerControllerTests {

    private RunnerController runnerController;
    private CreateRunnersPayload createRunnersPayload = new CreateRunnersPayload();
    private DeleteRunnersPayload deleteRunnersPayload = new DeleteRunnersPayload();
    private Runner runner1;
    private Runner runner2;

    @Mock
    private RunnerService runnerService;

    @Mock
    private RunnerRepository runnerRepository;

    @Mock
    private CloudFoundryOperationsFactory cloudFoundryOperationsFactory;

    @Mock
    private CloudFoundryOperations cloudFoundryOperations;

    @Before
    public void setUp() {
        String dockerUrl = "docker.mia.ulti.io/aeon/";
        this.runnerController = new RunnerController(
                this.runnerService,
                this.runnerRepository,
                this.cloudFoundryOperationsFactory,
                dockerUrl);

        PCF pcf = new PCF();
        pcf.apiHost = "apiHost";
        pcf.username = "username";
        pcf.password = "password";
        pcf.organization = "organization";
        pcf.space = "space";

        this.runner1 = new Runner("id1", "name1", "tenant");
        this.runner2 = new Runner("id2", "name2", "tenant");

        this.createRunnersPayload.count = 2;
        this.createRunnersPayload.type = "aeon-runner";
        this.createRunnersPayload.callbackUrl = "callback";
        this.createRunnersPayload.pcf = pcf;

        this.deleteRunnersPayload.callbackUrl = "callback";
        this.deleteRunnersPayload.pcf = pcf;

        when(this.cloudFoundryOperationsFactory.getCloudFoundryOperations(
                "apiHost",
                "username",
                "password",
                "organization",
                "space"))
                .thenReturn(this.cloudFoundryOperations);
        when(this.runnerService.deploy(
                dockerUrl + "aeon-runner",
                this.cloudFoundryOperations,
                "callback"))
                .thenReturn(
                        this.runner1,
                        this.runner2);

        when(this.runnerRepository.findAll()).thenReturn(Arrays.asList(
                this.runner1,
                this.runner2
        ));

        Optional<Runner> optionalRunner = Optional.of(this.runner2);
        when(this.runnerRepository.findById("runnerId")).thenReturn(optionalRunner);
    }

    @Test
    public void createRunnersTest() {
        // Arrange

        // Act
        ResponseEntity<List<Runner>> response = this.runnerController.createRunners(this.createRunnersPayload);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("id1", response.getBody().get(0).id);
        assertEquals("name1", response.getBody().get(0).name);
        assertEquals("id2", response.getBody().get(1).id);
        assertEquals("name2", response.getBody().get(1).name);
    }

    @Test
    public void deleteRunnerTest() {
        // Arrange

        // Act
        ResponseEntity response = this.runnerController.deleteRunner(
                "runnerId",
                false,
                this.deleteRunnersPayload);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(this.runnerService, times(1))
                .delete(this.runner2,
                        this.cloudFoundryOperations,
                        "callback",
                        false);
    }

    @Test
    public void deleteNonExistentRunnerTest() {
        // Arrange
        Optional<Runner> optionalRunner = Optional.empty();
        when(this.runnerRepository.findById("runnerId")).thenReturn(optionalRunner);

        // Act
        ResponseEntity response = this.runnerController.deleteRunner(
                "runnerId",
                false,
                this.deleteRunnersPayload);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(this.runnerService, times(0))
                .delete(any(Runner.class),
                        any(CloudFoundryOperations.class),
                        anyString(),
                        anyBoolean());
    }

    @Test
    public void deleteRunnersTest() {
        // Arrange

        // Act
        ResponseEntity response = this.runnerController.deleteRunners(
                false,
                this.deleteRunnersPayload);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(this.runnerService, times(2))
                .delete(any(),
                        any(),
                        anyString(),
                        anyBoolean());
        verify(this.runnerService, times(1))
                .delete(this.runner1,
                        this.cloudFoundryOperations,
                        "callback",
                        false);
        verify(this.runnerService, times(1))
                .delete(this.runner2,
                        this.cloudFoundryOperations,
                        "callback",
                        false);
    }

    @Test
    public void getRunnersTest() {
        // Arrange

        // Act
        ResponseEntity<List<Runner>> response = this.runnerController.getRunners();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("id1", response.getBody().get(0).id);
        assertEquals("name1", response.getBody().get(0).name);
        assertEquals("id2", response.getBody().get(1).id);
        assertEquals("name2", response.getBody().get(1).name);
    }

    @Test
    public void getRunnerTest() {
        // Arrange

        // Act
        ResponseEntity<Runner> response = this.runnerController.getRunner("runnerId");

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("id2", response.getBody().id);
        assertEquals("name2", response.getBody().name);
    }

    @Test
    public void getNonExistentRunnerTest() {
        // Arrange
        Optional<Runner> optionalRunner = Optional.empty();
        when(this.runnerRepository.findById("runnerId")).thenReturn(optionalRunner);

        // Act
        ResponseEntity<Runner> response = this.runnerController.getRunner("runnerId");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
