package browser;

import java.util.List;

/**
 * Class to create command execution body.
 */
public class ExecuteCommandBody {

    private String command;
    private List<Object> args;
    private Selector selector;

    /**
     * Creates a command execution body.
     * @param command Command
     * @param args Arguments
     * @param selector IByWeb arguments
     */
    public ExecuteCommandBody(String command, List<Object> args, Selector selector) {
        this.command = command;
        this.args = args;
        this.selector = selector;
    }

    /**
     * Get the arguments for the command.
     * @return Arguments
     */
    public List<Object> getArgs() {
        return args;
    }

    /**
     * Get the IByWeb arguments.
     * @return IByWeb arguments
     */
    public Selector getSelector() {
        return selector;
    }

    /**
     * Get the command.
     * @return Command
     */
    public String getCommand() {
        return command;
    }
}
