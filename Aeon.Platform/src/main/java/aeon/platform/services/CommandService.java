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
import aeon.core.common.web.selectors.By;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.models.Selector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Service for command execution.
 */
@Service
public class CommandService {

    /**
     * Creates the class constructor.
     * @param commandString Command class name
     * @return Constructor
     * @throws Exception Throws an exception if an error occurs
     */
    public Constructor createConstructor(String commandString) throws Exception {
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
     * Parses a parameter.
     * @param parameters Classes of parameters
     * @param args Arguments
     * @param selector Selector
     * @param i Index number
     * @return Object
     * @throws NullPointerException Throws an exception if Selector or any of its parameters is null
     */
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
            case "aeon.core.common.web.interfaces.IByWeb":
                if (selector != null && selector.getValue() != null && selector.getType() != null) {
                    param = parseIByWeb(selector);
                } else {
                    throw new NullPointerException("Selector and its value and type cannot be null");
                }
                break;
            case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                param = parseICommandInitializer(null);
                break;
        }

        return param;
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
    public ResponseEntity executeCommand(Constructor commandCons, ExecuteCommandBody body, AutomationInfo automationInfo, WebCommandExecutionFacade commandExecutionFacade) throws Exception {
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
     * Parses the IByWeb parameter.
     * @param selector Web Selector
     * @return IByWeb
     * @throws IllegalArgumentException Throws an exception if user tries to input type other than those accepted
     */
    private IBy parseIByWeb(Selector selector) throws IllegalArgumentException {
        IBy by;

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
                throw new IllegalArgumentException("Valid arguments for Selector type: css, data, da, jquery");
        }

        return by;
    }

    /**
     * Parses the ICommandInitializer parameter.
     * @param switchMechanism Switch mechanism
     * @return Web Command Initializer
     */
    private ICommandInitializer parseICommandInitializer(Iterable<IByWeb> switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
