package com.ultimatesoftware.aeon.platform.http.models;

import java.util.List;

/**
 * Class to create command execution body.
 */
public class ExecuteCommandBody {

    private String command;
    private List<Object> args;
    private String callbackUrl;

    /**
     * Constructs a default execute-command body. This is required for Jackson serializer when deserializing requests
     */
    public ExecuteCommandBody() {
    }

    /**
     * Constructs a execute-command body.
     *
     * @param command     The command string
     * @param args        The command arguments
     * @param callbackUrl Callback url string
     */
    public ExecuteCommandBody(String command, List<Object> args, String callbackUrl) {
        this.command = command;
        this.args = args;
        this.callbackUrl = callbackUrl;
    }

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
