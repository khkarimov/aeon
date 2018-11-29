package aeon.cloud.controllers;

import aeon.cloud.models.CreateRunnersPayload;
import aeon.cloud.models.DeleteRunnersPayload;
import aeon.cloud.models.Runner;
import aeon.cloud.repositories.RunnerRepository;
import aeon.cloud.services.IRunnerService;
import aeon.cloud.services.RunnerServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for runners.
 */
@RestController
@RequestMapping("api/v1")
public class RunnerController {

    private final RunnerServiceFactory runnerServiceFactory;
    private final RunnerRepository runnerRepository;
    private String dockerUrl;

    /**
     * Constructs a RunnerController.
     *
     * @param runnerServiceFactory Runner service factory.
     * @param runnerRepository     Runner repository.
     * @param dockerUrl            Base docker URL.
     */
    @Autowired
    public RunnerController(RunnerServiceFactory runnerServiceFactory,
                            RunnerRepository runnerRepository,
                            @Value("${aeon.docker.url}") String dockerUrl) {
        this.runnerServiceFactory = runnerServiceFactory;
        this.runnerRepository = runnerRepository;
        this.dockerUrl = dockerUrl;
    }

    /**
     * Creates runners.
     *
     * @param body Request payload
     * @return OK
     */
    @PostMapping("runners")
    public ResponseEntity<List<Runner>> createRunners(@Valid @RequestBody CreateRunnersPayload body) {

        IRunnerService runnerService = runnerServiceFactory.createRunnerService(body.credentials);

        List<Runner> runners = new ArrayList<>();

        for (int i = 0; i < body.count; i++) {
            runners.add(runnerService.deploy(dockerUrl + body.type, body.callbackUrl));
        }

        return new ResponseEntity<>(runners, HttpStatus.OK);
    }

    /**
     * Deletes a runner.
     *
     * @param runnerId ID of the runner to delete.
     * @param force    Force delete runner.
     * @param body     Request payload.
     * @return OK
     */
    @DeleteMapping("runners/{runnerId}")
    public ResponseEntity deleteRunner(
            @PathVariable String runnerId,
            @RequestParam(defaultValue = "false") boolean force,
            @Valid @RequestBody DeleteRunnersPayload body) {

        Optional<Runner> runner = runnerRepository.findById(runnerId);
        if (!runner.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        IRunnerService runnerService = runnerServiceFactory.createRunnerService(body.credentials);

        runnerService.delete(runner.get(), body.callbackUrl, force);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes every runner.
     *
     * @param force Force delete runners.
     * @param body  Request payload.
     * @return OK
     */
    @DeleteMapping("runners/delete-all")
    public ResponseEntity deleteRunners(
            @RequestParam(defaultValue = "false") boolean force,
            @Valid @RequestBody DeleteRunnersPayload body) {

        IRunnerService runnerService = runnerServiceFactory.createRunnerService(body.credentials);

        List<Runner> runners = runnerRepository.findAll();

        for (Runner runner : runners) {
            runnerService.delete(runner, body.callbackUrl, force);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves information about runners.
     *
     * @return The runner data.
     */
    @GetMapping("runners")
    public ResponseEntity<List<Runner>> getRunners() {
        return new ResponseEntity<>(runnerRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves information about a runner.
     *
     * @param runnerId ID of the runner to delete.
     * @return The runner data.
     */
    @GetMapping("runners/{runnerId}")
    public ResponseEntity<Runner> getRunner(@PathVariable String runnerId) {
        return runnerRepository.findById(runnerId)
                .map(runner -> new ResponseEntity<>(runner, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
