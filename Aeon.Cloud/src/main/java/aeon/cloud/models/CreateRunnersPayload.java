package aeon.cloud.models;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Model for payload of the endpoint for creating runners.
 */
public class CreateRunnersPayload {

    @Positive
    public int count;

    @NotBlank
    public String type;

    @NotNull
    public PCF pcf;

    @Nullable
    public String callbackUrl;
}
