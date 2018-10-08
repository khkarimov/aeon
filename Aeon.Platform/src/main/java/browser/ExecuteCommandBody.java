package browser;

import java.util.List;

/**
 * Class to create command execution body.
 */
public class ExecuteCommandBody {

    private String command;
    private List<Object> args;
    private List<String> byWebArgs;

    /**
     * Creates a command execution body.
     * @param command Command
     * @param args Arguments
     * @param byWebArgs IByWeb arguments
     */
    public ExecuteCommandBody(String command, List<Object> args, List<String> byWebArgs) {
        this.command = command;
        this.args = args;
        this.byWebArgs = byWebArgs;
    }

    /**
     * Get the arguments for the command.
     * @return Arguments
     */
    public List<Object> getArgs() {
        return args;
    }

    /**
     * Get the arguments for the IByWeb object.
     * @return IByWeb arguments
     */
    public List<String> getByWebArgs() {
        return byWebArgs;
    }

    /**
     * Get the command.
     * @return Command
     */
    public String getCommand() {
        return command;
    }
}
