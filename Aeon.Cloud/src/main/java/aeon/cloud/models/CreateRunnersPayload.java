package aeon.cloud.models;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.Map;

/**
 * Model for payload of the endpoint for creating runners.
 */
public class CreateRunnersPayload {

    @Positive
    public int count;

    @NotBlank
    public String type;

    @NotNull
    public Map<String, String> credentials;

    @Nullable
    public String callbackUrl;
}
