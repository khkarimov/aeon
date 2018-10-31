package aeon.platform.session;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.core.common.exceptions.CommandExecutionException;
import aeon.core.extensions.IProductTypeExtension;

import java.util.List;
import java.util.function.Supplier;

/**
 * Creates a Session object.
 */
public class Session implements ISession {

    private AutomationInfo automationInfo;
    private ICommandExecutionFacade commandExecutionFacade;
    private Supplier<List<IProductTypeExtension>> supplier;

    /**
     * Constructs a Session.
     * @param supplier Product type extension supplier
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     */
    public Session(AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade, Supplier<List<IProductTypeExtension>> supplier) {
        this.automationInfo = automationInfo;
        this.commandExecutionFacade = commandExecutionFacade;
        this.supplier = supplier;
    }

    @Override
    public Object executeCommand(String commandString, List<Object> args) {
        if (commandString == null) {
            throw new IllegalArgumentException("Command is null.");
        }

        Object command;

        List<IProductTypeExtension> extensions = supplier.get();

        for (IProductTypeExtension extension : extensions) {
            command = extension.createCommand(commandString, args);

            if (command != null) {
                if (CommandWithReturn.class.isAssignableFrom(command.getClass())) {
                    return commandExecutionFacade.execute(automationInfo, (CommandWithReturn) command);
                } else {
                    commandExecutionFacade.execute(automationInfo, (Command) command);
                }

                return null;
            }
        }

        throw new CommandExecutionException("Command is invalid.");
    }

    @Override
    public void quitSession() {
        commandExecutionFacade.execute(automationInfo, new QuitCommand());
    }
}
