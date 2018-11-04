package aeon.cloud.models;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Runner entity.
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
    public int getTime() {
        return this.time;
    }
}
