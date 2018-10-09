package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.By;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Command execution helper class.
 */
public class CommandHelper {

    /**
     * Creates the class constructor.
     * @param commandString Command class name
     * @return Constructor
     * @throws Exception Throws an exception if an error occurs
     */
    public static Constructor createConstructor(String commandString) throws Exception {
        Class command;

        if (commandString.equals("QuitCommand") || commandString.equals("CloseCommand")) {
            command = Class.forName("aeon.core.command.execution.commands." + commandString);
        } else {
            command = Class.forName("aeon.core.command.execution.commands.web." + commandString);
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
    public static ResponseEntity executeCommand(Constructor commandCons, ExecuteCommandBody body, AutomationInfo automationInfo, WebCommandExecutionFacade commandExecutionFacade) throws Exception {
        Class[] parameters = commandCons.getParameterTypes();
        List<Object> args = body.getArgs();
        WebSelector selector = body.getSelector();

        if ((parameters.length == 0) || (args != null && parameters.length == args.size())) {
            Object[] params = new Object[0];

            if (args != null) {
                params = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    switch (parameters[i].getName()) {
                        case "java.lang.String":
                            params[i] = parseString(args.get(i));
                            break;
                        case "java.lang.Boolean":
                            params[i] = parseBooleanObject(args.get(i));
                            break;
                        case "boolean":
                            params[i] = parseBoolean(args.get(i));
                            break;
                        case "aeon.core.common.web.interfaces.IByWeb":
                            if (selector != null && selector.getValue() != null && selector.getType() != null) {
                                params[i] = parseIByWeb(selector);
                            } else {
                                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                            }
                            break;
                        case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                            params[i] = parseICommandInitializer(null);
                            break;
                    }
                }
            }

            if (CommandWithReturn.class.isAssignableFrom(commandCons.getDeclaringClass())) {
                commandExecutionFacade.execute(automationInfo, (CommandWithReturn) commandCons.newInstance(params));

                return new ResponseEntity<>(HttpStatus.OK);
            } else if (Command.class.isAssignableFrom(commandCons.getDeclaringClass())) {
                commandExecutionFacade.execute(automationInfo, (Command) commandCons.newInstance(params));

                return new ResponseEntity<>(HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Parses the String parameter.
     * @param arg Argument object
     * @return Argument as a String
     */
    public static String parseString(Object arg) {
        return (String) arg;
    }

    /**
     * Parses the Boolean parameter.
     * @param arg Argument object
     * @return Argument as a Boolean
     */
    public static Boolean parseBooleanObject(Object arg) {
        return (Boolean) arg;
    }

    /**
     * Parses the boolean parameter.
     * @param arg Argument object
     * @return Argument as a boolean
     */
    public static boolean parseBoolean(Object arg) {
        return (boolean) arg;
    }

    /**
     * Parses the IByWeb parameter.
     * @param selector Web Selector
     * @return IByWeb
     * @throws IllegalArgumentException Throws an exception if user tries to input type other than those accepted
     */
    public static IByWeb parseIByWeb(WebSelector selector) throws IllegalArgumentException {
        IByWeb by;

        String value = selector.getValue();
        String type = selector.getType();

        switch (type.toLowerCase()) {
            case "css":
                by = By.cssSelector(value);
                break;
            case "data":
                by = By.dataAutomationAttribute(value);
                break;
            case "da":
                by = By.da(value);
                break;
            case "jquery":
                by = By.jQuery(value);
                break;
            default:
                throw new IllegalArgumentException("Valid arguments for WebSelector type: css, data, da, jquery");
        }

        return by;
    }

    /**
     * Parses the ICommandInitializer parameter.
     * @param switchMechanism Switch mechanism
     * @return Web Command Initializer
     */
    public static ICommandInitializer parseICommandInitializer(Iterable<IByWeb> switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
