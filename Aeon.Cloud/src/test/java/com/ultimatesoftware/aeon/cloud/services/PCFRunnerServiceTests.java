package com.ultimatesoftware.aeon.cloud.services;

import com.ultimatesoftware.aeon.cloud.models.DeploymentTime;
import com.ultimatesoftware.aeon.cloud.models.Runner;
import com.ultimatesoftware.aeon.cloud.repositories.DeploymentTimeRepository;
import com.ultimatesoftware.aeon.cloud.repositories.RunnerRepository;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.function.Consumer;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class PCFRunnerServiceTests {

    private PCFRunnerService runnerService;

    @Mock
    private RunnerRepository runnerRepository;

    @Mock
    private DeploymentTimeRepository deploymentTimeRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private DefaultCloudFoundryOperations cloudFoundryOperations;

    @Mock
    private Applications applications;

    @Mock
    private Mono<Void> result;

    @Mock
    private Mono<Void> deleteResult;

    @Mock
    private Mono<ApplicationDetail> applicationDetailResult;

    @Mock
    private ApplicationDetail applicationDetail;

    @Mock
    private InstanceDetail instanceDetail;

    private Runner runner;

    @Captor
    private ArgumentCaptor<Runner> argument;

    @Captor
    private ArgumentCaptor<Consumer<Void>> doOnSuccess;

    @Captor
    private ArgumentCaptor<Consumer<Throwable>> doOnError;

    @Captor
    private ArgumentCaptor<PushApplicationManifestRequest> pushApplicationManifestRequest;

    @Captor
    private ArgumentCaptor<GetApplicationRequest> getApplicationRequest;

    @Captor
    private ArgumentCaptor<DeleteApplicationRequest> deleteApplicationRequest;

    @Captor
    private ArgumentCaptor<DeploymentTime> deploymentTime;

    @Captor
    private ArgumentCaptor<Consumer<ApplicationDetail>> subscribe;

    @Before
    public void setUp() {
        runnerService = new PCFRunnerService(
                this.runnerRepository,
                this.deploymentTimeRepository,
                this.notificationService,
                this.cloudFoundryOperations);

        this.runner = new Runner("id", "name", "tenant");

        when(this.cloudFoundryOperations.applications()).thenReturn(this.applications);
        when(this.cloudFoundryOperations.getOrganization()).thenReturn("organization");
        when(this.applications.pushManifest(this.pushApplicationManifestRequest.capture())).thenReturn(this.result);
        when(this.applications.delete(this.deleteApplicationRequest.capture())).thenReturn(this.deleteResult);
        when(this.applications.get(this.getApplicationRequest.capture())).thenReturn(this.applicationDetailResult);
        when(this.result.doOnSuccess(this.doOnSuccess.capture())).thenReturn(this.result);
        when(this.result.doOnError(this.doOnError.capture())).thenReturn(this.result);
        when(this.deleteResult.doOnSuccess(this.doOnSuccess.capture())).thenReturn(this.deleteResult);
        when(this.deleteResult.doOnError(this.doOnError.capture())).thenReturn(this.deleteResult);
        when(this.applicationDetailResult.subscribe(this.subscribe.capture())).thenReturn(null);

        when(this.deploymentTimeRepository.insert(this.deploymentTime.capture())).thenReturn(null);
        when(this.applicationDetail.getInstanceDetails()).thenReturn(Collections.singletonList(this.instanceDetail));
        when(this.applicationDetail.getUrls()).thenReturn(Collections.singletonList("URL"));
        when(this.applicationDetail.getId()).thenReturn("guid");
        when(this.instanceDetail.getState()).thenReturn("RUNNING");
    }

    @Test
    public void createRunnerSuccessTest() {
        // Arrange

        // Act
        this.runnerService.deploy(
                "dockerImage",
                "callback"
        );

        // Assert
        verify(this.runnerRepository, times(1))
                .insert(this.argument.capture());
        String runnerId = this.argument.getValue().id;
        assertNotNull(runnerId);
        assertTrue(!runnerId.trim().isEmpty());
        assertEquals("aeon-runner-" + runnerId, this.argument.getValue().name);
        assertEquals("organization", this.argument.getValue().tenant);
        assertEquals("PENDING", this.argument.getValue().status);
        assertNull(argument.getValue().apiUrl);
        assertNull(argument.getValue().uiUrl);
        assertNull(argument.getValue().baseUrl);
        assertNull(argument.getValue().metaData.get("guid"));

        ApplicationManifest manifest = this.pushApplicationManifestRequest.getValue().getManifests().get(0);
        assertEquals("aeon-runner-" + runnerId, manifest.getName());
        assertEquals("dockerImage", manifest.getDocker().getImage());
        assertEquals("dockerImage", manifest.getDocker().getImage());
        assertEquals(ApplicationHealthCheck.HTTP, manifest.getHealthCheckType());
        verify(this.deploymentTimeRepository, times(0)).insert(any(DeploymentTime.class));
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.runnerRepository, times(0)).save(any());
        verify(this.result, times(1)).subscribe();

        // Act: Trigger deployment success
        this.doOnSuccess.getValue().accept(null);

        // Assert
        verify(this.deploymentTimeRepository, times(1)).insert(any(DeploymentTime.class));
        assertNotNull(this.deploymentTime.getValue());

        assertEquals("aeon-runner-" + runnerId, this.getApplicationRequest.getValue().getName());

        // Act: Trigger application detail result
        this.subscribe.getValue().accept(this.applicationDetail);

        // Assert
        assertEquals("RUNNING", this.argument.getValue().status);
        assertEquals("https://URL/api/v1/", this.argument.getValue().apiUrl);
        assertEquals("https://URL/vnc_lite.html", this.argument.getValue().uiUrl);
        assertEquals("https://URL", this.argument.getValue().baseUrl);
        assertEquals("guid", argument.getValue().metaData.get("guid"));
        verify(this.runnerRepository, times(1)).save(this.argument.getValue());
        verify(this.notificationService, times(1)).notify(
                NotificationService.EventType.RUNNER_DEPLOYED,
                "callback",
                this.argument.getValue());
    }

    @Test
    public void createRunnerFailureTest() {
        // Arrange

        // Act
        this.runnerService.deploy(
                "dockerImage",
                "callback"
        );

        // Assert
        verify(this.runnerRepository, times(1))
                .insert(this.argument.capture());
        String runnerId = this.argument.getValue().id;
        assertNotNull(runnerId);
        assertTrue(!runnerId.trim().isEmpty());
        assertEquals("aeon-runner-" + runnerId, this.argument.getValue().name);
        assertEquals("organization", this.argument.getValue().tenant);
        assertEquals("PENDING", this.argument.getValue().status);
        assertNull(argument.getValue().apiUrl);
        assertNull(argument.getValue().uiUrl);
        assertNull(argument.getValue().baseUrl);
        assertNull(argument.getValue().metaData.get("guid"));

        ApplicationManifest manifest = this.pushApplicationManifestRequest.getValue().getManifests().get(0);
        assertEquals("aeon-runner-" + runnerId, manifest.getName());
        assertEquals("dockerImage", manifest.getDocker().getImage());
        assertEquals(ApplicationHealthCheck.HTTP, manifest.getHealthCheckType());
        assertEquals("/api/admin/healthcheck", manifest.getHealthCheckHttpEndpoint());
        assertEquals(2048, (int) manifest.getDisk());
        verify(this.deploymentTimeRepository, times(0)).insert(any(DeploymentTime.class));
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.runnerRepository, times(0)).save(any());
        verify(this.result, times(1)).subscribe();

        // Act: Trigger deployment failure
        this.doOnError.getValue().accept(new RuntimeException("test message"));

        // Assert
        verify(this.deploymentTimeRepository, times(0)).insert(any(DeploymentTime.class));

        assertEquals("FAILED", this.argument.getValue().status);
        assertEquals("test message", this.argument.getValue().message);
        assertNull(argument.getValue().apiUrl);
        assertNull(argument.getValue().uiUrl);
        assertNull(argument.getValue().baseUrl);
        assertNull(argument.getValue().metaData.get("guid"));
        assertEquals("aeon-runner-" + runnerId, this.deleteApplicationRequest.getValue().getName());
        verify(this.runnerRepository, times(1)).save(this.argument.getValue());
        verify(this.notificationService, times(1)).notify(
                NotificationService.EventType.RUNNER_FAILED,
                "callback",
                this.argument.getValue());
        verify(this.deleteResult, times(1)).subscribe();
    }

    @Test
    public void deleteRunnerSuccessTest() {
        // Arrange

        // Act
        this.runnerService.delete(
                this.runner,
                "callback",
                false
        );

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.deleteResult, times(1)).subscribe();
        assertEquals(runner.name, this.deleteApplicationRequest.getValue().getName());
        assertTrue(this.deleteApplicationRequest.getValue().getDeleteRoutes());
        assertEquals("DELETING", this.runner.status);
        assertNull(this.runner.message);

        // Act: Trigger deletion success
        this.doOnSuccess.getValue().accept(null);

        // Assert
        verify(this.runnerRepository, times(1)).delete(this.runner);
        verify(this.notificationService, times(1)).notify(
                NotificationService.EventType.RUNNER_DELETED,
                "callback",
                this.runner);
    }

    @Test
    public void deleteRunnerFailureTest() {
        // Arrange
        runner.baseUrl = "url";

        // Act
        this.runnerService.delete(
                this.runner,
                "callback",
                false
        );

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.deleteResult, times(1)).subscribe();
        assertEquals(runner.name, this.deleteApplicationRequest.getValue().getName());
        assertTrue(this.deleteApplicationRequest.getValue().getDeleteRoutes());
        assertEquals("DELETING", this.runner.status);
        assertNull(this.runner.message);

        // Act: Trigger deletion failure
        this.doOnError.getValue().accept(new RuntimeException("test message"));

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());

        assertEquals("RUNNING", this.runner.status);
        assertEquals("Delete failed: test message. Try using force=true", this.runner.message);

        verify(this.runnerRepository, times(2)).save(this.runner);
        verify(this.notificationService, times(1)).notify(
                NotificationService.EventType.RUNNER_DELETION_FAILED,
                "callback",
                this.runner);
    }

    @Test
    public void deleteFailedRunnerFailureTest() {
        // Arrange

        // Act
        this.runnerService.delete(
                this.runner,
                "callback",
                false
        );

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.deleteResult, times(1)).subscribe();
        assertEquals(runner.name, this.deleteApplicationRequest.getValue().getName());
        assertTrue(this.deleteApplicationRequest.getValue().getDeleteRoutes());
        assertEquals("DELETING", this.runner.status);
        assertNull(this.runner.message);

        // Act: Trigger deletion failure
        this.doOnError.getValue().accept(new RuntimeException("test message"));

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());

        assertEquals("FAILED", this.runner.status);
        assertEquals("Delete failed: test message. Try using force=true", this.runner.message);

        verify(this.runnerRepository, times(2)).save(this.runner);
        verify(this.notificationService, times(1)).notify(
                NotificationService.EventType.RUNNER_DELETION_FAILED,
                "callback",
                this.runner);
    }

    @Test
    public void deleteRunnerFailureWithForceTest() {
        // Arrange
        InOrder inOrder = Mockito.inOrder(runnerRepository, notificationService);

        // Act
        this.runnerService.delete(
                this.runner,
                "callback",
                true
        );

        // Assert
        verify(this.runnerRepository, times(0)).delete(any());
        verify(this.notificationService, times(0)).notify(any(), anyString(), any());
        verify(this.deleteResult, times(1)).subscribe();
        assertEquals(runner.name, this.deleteApplicationRequest.getValue().getName());
        assertTrue(this.deleteApplicationRequest.getValue().getDeleteRoutes());
        assertEquals("DELETING", this.runner.status);
        assertNull(this.runner.message);

        // Act: Trigger deletion failure
        this.doOnError.getValue().accept(new RuntimeException("test message"));

        // Assert
        inOrder.verify(runnerRepository, times(2)).save(this.runner);
        inOrder.verify(notificationService).notify(
                NotificationService.EventType.RUNNER_DELETION_FAILED,
                "callback",
                this.runner);
        inOrder.verify(runnerRepository).delete(this.runner);
        inOrder.verifyNoMoreInteractions();
    }
}
