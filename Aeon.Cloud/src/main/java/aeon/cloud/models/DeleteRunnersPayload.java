package aeon.cloud.models;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Class to create session body.
 */
public class DeleteRunnersPayload {

    @NotNull
    public PCF pcf;

    @Nullable
    public String callbackUrl;
}
