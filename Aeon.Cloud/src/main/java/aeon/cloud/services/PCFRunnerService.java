package aeon.cloud.services;

import aeon.cloud.models.DeploymentTime;
import aeon.cloud.models.Runner;
import aeon.cloud.repositories.DeploymentTimeRepository;
import aeon.cloud.repositories.RunnerRepository;
import org.bson.types.ObjectId;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.*;
import reactor.core.publisher.Mono;

/**
 * Service for managing runners in PCF.
 */
public class PCFRunnerService implements IRunnerService {

    private final RunnerRepository runnerRepository;
    private final DeploymentTimeRepository deploymentTimeRepository;
    private final NotificationService notificationService;
    private final CloudFoundryOperations cloudFoundryOperations;
    private static final String HTTPS = "https://";

    /**
     * Constructor.
     *
     * @param runnerRepository         The runner repository to use.
     * @param deploymentTimeRepository The deploymentTime repository to use.
     * @param notificationService      The notification service to use.
     * @param cloudFoundryOperations   CloudFoundryOperations to use.
     */
    PCFRunnerService(RunnerRepository runnerRepository,
                     DeploymentTimeRepository deploymentTimeRepository,
                     NotificationService notificationService,
                     CloudFoundryOperations cloudFoundryOperations) {
        this.runnerRepository = runnerRepository;
        this.deploymentTimeRepository = deploymentTimeRepository;
        this.notificationService = notificationService;
        this.cloudFoundryOperations = cloudFoundryOperations;
    }

    @Override
    public Runner deploy(String dockerImage, String callbackUrl) {

        ObjectId runnerId = new ObjectId();
        String runnerName = "aeon-runner-" + runnerId;
        Runner runner = new Runner(
                runnerId.toString(),
                runnerName,
                ((DefaultCloudFoundryOperations) cloudFoundryOperations).getOrganization());

        this.runnerRepository.insert(runner);

        ApplicationManifest manifest = ApplicationManifest.builder()
                .name(runnerName)
                .docker(Docker.builder()
                        .image(dockerImage)
                        .build())
                .healthCheckType(ApplicationHealthCheck.HTTP)
                .healthCheckHttpEndpoint("api/admin/healthcheck")
                .build();

        PushApplicationManifestRequest request = PushApplicationManifestRequest.builder()
                .manifest(manifest)
                .build();


        Mono<Void> result = cloudFoundryOperations.applications().pushManifest(request);

        result.doOnSuccess(
                success -> deploymentSuccessful(callbackUrl, runner)
        ).doOnError(
                error -> deploymentFailed(error, callbackUrl, runner)
        ).subscribe();

        return runner;
    }

    @Override
    public void delete(Runner runner, String callbackUrl, boolean force) {

        DeleteApplicationRequest request = DeleteApplicationRequest.builder()
                .name(runner.name)
                .deleteRoutes(true)
                .build();

        runner.status = "DELETING";
        runner.message = null;
        this.runnerRepository.save(runner);

        Mono<Void> result = cloudFoundryOperations.applications().delete(request);

        result.doOnSuccess(
                success -> deleteSuccessful(callbackUrl, runner)
        ).doOnError(
                error -> deleteFailed(error, callbackUrl, runner, force)
        ).subscribe();
    }

    private void deploymentSuccessful(String callbackUrl, Runner runner) {

        ObjectId id = new ObjectId(runner.id);
        DeploymentTime deploymentTime = new DeploymentTime(new ObjectId().getTimestamp() - id.getTimestamp());
        this.deploymentTimeRepository.insert(deploymentTime);

        GetApplicationRequest getApplicationRequest = GetApplicationRequest.builder()
                .name(runner.name)
                .build();
        cloudFoundryOperations.applications()
                .get(getApplicationRequest)
                .subscribe(applicationDetail -> {
                    runner.status = applicationDetail.getInstanceDetails().get(0).getState();
                    runner.apiUrl = HTTPS + applicationDetail.getUrls().get(0) + "/api/v1/";
                    runner.uiUrl = HTTPS + applicationDetail.getUrls().get(0) + "/vnc_lite.html";
                    runner.baseUrl = HTTPS + applicationDetail.getUrls().get(0);
                    runner.metaData.put("guid", applicationDetail.getId());
                    this.runnerRepository.save(runner);
                    this.notificationService.notify(
                            NotificationService.EventType.RUNNER_DEPLOYED,
                            callbackUrl,
                            runner);
                });
    }

    private void deploymentFailed(Throwable error, String callbackUrl, Runner runner) {

        runner.status = "FAILED";
        runner.message = error.getMessage();
        this.runnerRepository.save(runner);
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_FAILED,
                callbackUrl,
                runner);

        DeleteApplicationRequest deleteApplicationRequest = DeleteApplicationRequest.builder()
                .name(runner.name)
                .deleteRoutes(true)
                .build();
        cloudFoundryOperations.applications()
                .delete(deleteApplicationRequest)
                .subscribe();
    }

    private void deleteSuccessful(String callbackUrl, Runner runner) {

        this.runnerRepository.delete(runner);
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DELETED,
                callbackUrl,
                runner);
    }

    private void deleteFailed(Throwable error, String callbackUrl, Runner runner, boolean force) {

        if (force) {
            this.runnerRepository.delete(runner);
        }

        if (runner.baseUrl != null) {
            runner.status = "RUNNING";
        } else {
            runner.status = "FAILED";
        }
        runner.message = "Delete failed: " + error.getMessage() + ". Try using force=true";
        this.runnerRepository.save(runner);
        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DELETION_FAILED,
                callbackUrl,
                runner);
    }
}
