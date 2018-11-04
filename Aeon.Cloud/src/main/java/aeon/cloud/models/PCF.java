package aeon.cloud.models;


import javax.validation.constraints.NotEmpty;

/**
 * PCF credentials.
 */
public class PCF {

    @NotEmpty
    public String apiHost;

    @NotEmpty
    public String username;

    @NotEmpty
    public String password;

    @NotEmpty
    public String organization;

    @NotEmpty
    public String space;
}
