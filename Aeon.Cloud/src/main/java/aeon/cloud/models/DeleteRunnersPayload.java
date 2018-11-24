package aeon.cloud.models;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

import java.util.Map;

/**
 * Model for payload of the endpoints for deleting runners.
 */
public class DeleteRunnersPayload {

    @NotNull
    public Map<String, String> credentials;

    @Nullable
    public String callbackUrl;
}
