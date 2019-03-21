package com.ultimatesoftware.aeon.cloud.services;

import com.ultimatesoftware.aeon.cloud.models.Runner;

/**
 * Interface that runner deployment services have to implement.
 */
public interface IRunnerService {

    /**
     * Deploys a runner.
     *
     * @param dockerImage The docker image to use.
     * @param callbackUrl Callback URL for notifications.
     * @return The runner that is deployed.
     */
    Runner deploy(String dockerImage, String callbackUrl);

    /**
     * Delete a runner.
     *
     * @param runner      The runner to delete.
     * @param callbackUrl Callback URL for notifications.
     * @param force       Force delete runners.
     */
    void delete(Runner runner, String callbackUrl, boolean force);
}
