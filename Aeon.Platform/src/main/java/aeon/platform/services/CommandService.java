package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.extensions.IProductTypeExtension;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.models.Selector;

import javax.inject.Inject;

import java.util.List;
import java.util.function.Supplier;

/**
 * Service for command execution.
 */
public class CommandService {

    private Supplier<List<IProductTypeExtension>> supplier;

    /**
     * Constructs a Command Service.
     * @param supplier Product type extensions supplier
     */
    @Inject
    public CommandService(Supplier<List<IProductTypeExtension>> supplier) {
        this.supplier = supplier;
    }

    /**
     * Executes a command.
     * @param commandString Command String
     * @param body Execute command body
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    public Object executeCommand(String commandString, ExecuteCommandBody body, AutomationInfo automationInfo, ICommandExecutionFacade commandExecutionFacade) throws Exception {
        List<Object> args = body.getArgs();
        Selector selector = body.getSelector();
        String value;
        String type;

        if (selector != null) {
            value = selector.getValue();
            type = selector.getType();
        } else {
            value = null;
            type = null;
        }

        Object command = null;

        List<IProductTypeExtension> extensions = supplier.get();

        for (int i = 0; i < extensions.size(); i++) {
            command = extensions.get(i).createCommand(commandString, args, value, type);

            if (command != null) {
                break;
            }
        }

        if (command == null) {
            throw new IllegalArgumentException("Command is invalid.");
        }

            if (CommandWithReturn.class.isAssignableFrom(command.getClass())) {
                return commandExecutionFacade.execute(automationInfo, (CommandWithReturn) command);
            } else if (Command.class.isAssignableFrom(command.getClass())) {
                commandExecutionFacade.execute(automationInfo, (Command) command);

                return null;
            }

        throw new Exception();
    }
}
