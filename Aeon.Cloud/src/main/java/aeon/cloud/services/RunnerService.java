package aeon.cloud.services;

import aeon.cloud.models.DeploymentTime;
import aeon.cloud.models.Runner;
import aeon.cloud.repositories.DeploymentTimeRepository;
import aeon.cloud.repositories.RunnerRepository;
import org.bson.types.ObjectId;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.operations.applications.DeleteApplicationRequest;
import org.cloudfoundry.operations.applications.GetApplicationRequest;
import org.cloudfoundry.operations.applications.PushApplicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Service for managing runners.
 */
@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final DeploymentTimeRepository deploymentTimeRepository;
    private final NotificationService notificationService;

    /**
     * Constructor.
     *
     * @param runnerRepository         The runner repository to use.
     * @param deploymentTimeRepository The deploymentTime repository to use.
     * @param notificationService      The notification service to use.
     */
    @Autowired
    public RunnerService(RunnerRepository runnerRepository,
                         DeploymentTimeRepository deploymentTimeRepository,
                         NotificationService notificationService) {
        this.runnerRepository = runnerRepository;
        this.deploymentTimeRepository = deploymentTimeRepository;
        this.notificationService = notificationService;
    }

    /**
     * Deploys a runner.
     *
     * @param dockerImage            The docker image to use.
     * @param cloudFoundryOperations CloudFoundryOperations to use.
     * @param callbackUrl            Callback URL for notifications.
     * @return The runner that is deployed.
     */
    public Runner deploy(String dockerImage,
                         CloudFoundryOperations cloudFoundryOperations,
                         String callbackUrl) {

        ObjectId runnerId = new ObjectId();
        String runnerName = "aeon-runner-" + runnerId;
        Runner runner = new Runner(
                runnerId.toString(),
                runnerName,
                ((DefaultCloudFoundryOperations) cloudFoundryOperations).getOrganization());

        this.runnerRepository.insert(runner);

        PushApplicationRequest request = PushApplicationRequest.builder()
                .name(runnerName)
                .dockerImage(dockerImage)
                .build();

        Mono<Void> result = cloudFoundryOperations.applications().push(request);

        result.doOnSuccess(
                success -> deploymentSuccessful(cloudFoundryOperations, callbackUrl, runner)
        ).doOnError(
                error -> deploymentFailed(error, cloudFoundryOperations, callbackUrl, runner)
        ).subscribe();

        return runner;
    }

    /**
     * Delete a runner.
     *
     * @param runner                 The runner to delete.
     * @param cloudFoundryOperations CloudFoundryOperations to use.
     * @param callbackUrl            Callback URL for notifications.
     * @param force                  Force delete runners.
     */
    public void delete(Runner runner,
                       CloudFoundryOperations cloudFoundryOperations,
                       String callbackUrl,
                       boolean force) {

        DeleteApplicationRequest request = DeleteApplicationRequest.builder()
                .name(runner.name)
                .deleteRoutes(true)
                .build();

        runner.status = "DELETING";
        this.runnerRepository.save(runner);

        Mono<Void> result = cloudFoundryOperations.applications().delete(request);

        result.doOnSuccess(
                success -> deleteSuccessful(callbackUrl, runner)
        ).doOnError(
                error -> deleteFailed(callbackUrl, runner, force)
        ).subscribe();
    }

    private void deploymentSuccessful(CloudFoundryOperations cloudFoundryOperations,
                                      String callbackUrl,
                                      Runner runner) {

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
                    runner.apiUrl = "https://" + applicationDetail.getUrls().get(0) + "/api/v1/";
                    runner.uiUrl = "https://" + applicationDetail.getUrls().get(0) + "/vnc_lite.html";
                    runner.baseUrl = "https://" + applicationDetail.getUrls().get(0);
                    runner.pcfMetaData.guid = applicationDetail.getId();
                    this.runnerRepository.save(runner);
                    this.notificationService.notify(
                            NotificationService.EventType.RUNNER_DEPLOYED,
                            callbackUrl,
                            runner);
                });
    }

    private void deploymentFailed(Throwable error, CloudFoundryOperations cloudFoundryOperations,
                                  String callbackUrl,
                                  Runner runner) {
        runner.status = "FAILED";
        runner.errorMessage = error.getMessage();
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

    private void deleteFailed(String callbackUrl, Runner runner, boolean force) {

        if (force) {
            this.runnerRepository.delete(runner);
        }

        this.notificationService.notify(
                NotificationService.EventType.RUNNER_DELETION_FAILED,
                callbackUrl,
                runner);
    }
}
