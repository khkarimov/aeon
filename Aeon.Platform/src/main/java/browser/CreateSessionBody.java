package browser;

import java.util.List;
import java.util.Properties;

/**
 * Class to create session body.
 */
public class CreateSessionBody {

    private Properties settings;
    private String command;
    private List<Object> args;
    private ByWebArgs byWebArgs;

    /**
     * Creates a session body.
     * @param settings Settings
     * @param command Command
     * @param args Arguments
     * @param byWebArgs IByWeb arguments
     */
    public CreateSessionBody(Properties settings, String command, List<Object> args, ByWebArgs byWebArgs) {
        this.settings = settings;
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
    public ByWebArgs getByWebArgs() {
        return byWebArgs;
    }

    /**
     * Get the command.
     * @return Command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Get the settings.
     * @return Settings
     */
    public Properties getSettings() {
        return settings;
    }
}
