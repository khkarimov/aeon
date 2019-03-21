package com.ultimatesoftware.aeon.cloud.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Entity for deployment time records.
 */
@Document(collection = "aeonCloudDeploymentTime")
public class DeploymentTime {

    @NotNull
    private int time;

    /**
     * Constructor.
     *
     * @param time Deployment Time
     */
    public DeploymentTime(int time) {
        this.time = time;
    }

    /**
     * Getter for the time.
     *
     * @return The deployment time.
     */
    int getTime() {
        return this.time;
    }
}
