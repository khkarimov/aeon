package aeon.platform.http.models;

import java.util.List;

/**
 * Class to create command execution body.
 */
public class ExecuteCommandBody {

    private String command;
    private List<Object> args;
    private String callbackUrl;

    /**
     * Get the arguments for the command.
     *
     * @return Arguments
     */
    public List<Object> getArgs() {
        return args;
    }

    /**
     * Get the command.
     *
     * @return Command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the callback URL.
     *
     * @return URL
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }
}
