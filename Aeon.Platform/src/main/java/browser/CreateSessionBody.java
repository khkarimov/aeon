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

    /**
     * Get the arguments for the command.
     * @return Arguments
     */
    public List<Object> getArgs() {
        return args;
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
