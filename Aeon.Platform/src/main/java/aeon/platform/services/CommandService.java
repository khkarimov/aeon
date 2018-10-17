package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.extensions.IProductTypeExtension;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.models.Selector;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Service for command execution.
 */
public class CommandService {

    /**
     * Creates the class constructor.
     * @param commandString Command class name
     * @return Constructor
     * @throws Exception Throws an exception if an error occurs
     */
    public Constructor getCommandInstance(String commandString) throws Exception {
        Class<?> command = null;

        if (commandString.equals("QuitCommand") || commandString.equals("CloseCommand")) {
            command = Class.forName("aeon.core.command.execution.commands." + commandString);
        } else {
            PluginManager pluginManager = new DefaultPluginManager();
            List<IProductTypeExtension> extensions = pluginManager.getExtensions(IProductTypeExtension.class);

            while (command == null && !extensions.isEmpty()) {
                command = extensions.get(0).createCommand(commandString);
                extensions.remove(0);
            }

            if (command == null) {
                throw new IllegalArgumentException("Command is invalid.");
            }
        }

        Constructor[] cons = command.getConstructors();
        Class[] parameters = cons[0].getParameterTypes();

        return command.getConstructor(parameters);
    }

    /**
     * Executes a command.
     * @param commandCons Command constructor
     * @param body Execute command body
     * @param automationInfo Automation info
     * @param commandExecutionFacade Command execution facade
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    public Object executeCommand(Constructor commandCons, ExecuteCommandBody body, AutomationInfo automationInfo, WebCommandExecutionFacade commandExecutionFacade) throws Exception {
        Class[] parameters = commandCons.getParameterTypes();
        List<Object> args = body.getArgs();
        Selector selector = body.getSelector();

        if ((parameters.length == 0) || (args != null && parameters.length == args.size())) {
            Object[] params = new Object[0];

            if (args != null) {
                params = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    params[i] = parseParameter(parameters, args, selector, i);
                }
            }

            if (CommandWithReturn.class.isAssignableFrom(commandCons.getDeclaringClass())) {
                return commandExecutionFacade.execute(automationInfo, (CommandWithReturn) commandCons.newInstance(params));
            } else if (Command.class.isAssignableFrom(commandCons.getDeclaringClass())) {
                commandExecutionFacade.execute(automationInfo, (Command) commandCons.newInstance(params));

                return null;
            }
        }

        throw new Exception();
    }

    private Object parseParameter(Class[] parameters, List<Object> args, Selector selector, int i) throws NullPointerException {
        Object param = null;

        switch (parameters[i].getName()) {
            case "java.lang.String":
                param = (String) args.get(i);
                break;
            case "java.lang.Boolean":
                param = (Boolean) args.get(i);
                break;
            case "boolean":
                param = (boolean) args.get(i);
                break;
            case "int":
                param = (int) args.get(i);
                break;
            case "double":
                param = (double) args.get(i);
                break;
            case "aeon.core.common.web.interfaces.IByWeb":
                if (selector != null && selector.getValue() != null && selector.getType() != null) {
                    param = parseSelector(selector);
                } else {
                    throw new NullPointerException("Selector and its value and type cannot be null");
                }
                break;
            case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                // switchMechanism is always null
                param = parseICommandInitializer(null);
                break;
        }

        return param;
    }

    private IBy parseSelector(Selector selector) throws IllegalArgumentException {
        IBy by = null;

        String value = selector.getValue();
        String type = selector.getType();

        PluginManager pluginManager = new DefaultPluginManager();
        List<IProductTypeExtension> extensions = pluginManager.getExtensions(IProductTypeExtension.class);

        while (by == null && !extensions.isEmpty()) {
            by = extensions.get(0).createSelector(value, type);
            extensions.remove(0);
        }

        if (by == null) {
            throw new IllegalArgumentException("Type is invalid.");
        }

        return by;
    }

    private ICommandInitializer parseICommandInitializer(Iterable<IByWeb> switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
