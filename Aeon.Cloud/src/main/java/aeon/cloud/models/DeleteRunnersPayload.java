package aeon.cloud.models;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Model for payload of the endpoints for deleting runners.
 */
public class DeleteRunnersPayload {

    @NotNull
    public PCF pcf;

    @Nullable
    public String callbackUrl;
}
