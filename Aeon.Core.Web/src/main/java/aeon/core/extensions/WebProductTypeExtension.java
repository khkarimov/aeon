package aeon.core.extensions;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.By;
import org.pf4j.Extension;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Web product extension.
 */
@Extension
public class WebProductTypeExtension implements IProductTypeExtension {

    @Override
    public IBy createSelector(String value, String type) {
        switch (type.toLowerCase()) {
            case "css":
                return By.cssSelector(value);
            case "data":
                return By.dataAutomationAttribute(value);
            case "da":
                return By.da(value);
            case "jquery":
                return By.jQuery(value);
            default:
                return null;
        }
    }

    @Override
    public Object createCommand(String commandString, List<Object> args, String value, String type) {
        Object command = null;
        Class<?> commandClass;

        try {
            if (commandString.equals("QuitCommand") || commandString.equals("CloseCommand")) {
                commandClass = Class.forName("aeon.core.command.execution.commands." + commandString);
            } else {
                commandClass = Class.forName("aeon.core.command.execution.commands.web." + commandString);
            }
        } catch (Exception e) {
            return null;
        }

        Constructor[] constructors = commandClass.getConstructors();
        Class[] parameters = constructors[0].getParameterTypes();
        Constructor commandConstructor;

        try {
            commandConstructor = commandClass.getConstructor(parameters);
        } catch (Exception e) {
            return null;
        }

        if (parameters.length == 0 || (args != null && parameters.length == args.size())) {
            Object[] params = new Object[0];

            if (args != null) {
                params = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    try {
                        params[i] = createParameter(parameters, args, value, type, i);
                    } catch (Exception e) {
                        return null;
                    }
                }
            }

            try {
                command = commandConstructor.newInstance(params);
            } catch (Exception e) {
                return null;
            }
        }

        return command;
    }

    @Override
    public Object createParameter(Class[] parameters, List<Object> args, String value, String type, int i) {
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
                if (value != null && type != null) {
                    param = createSelector(value, type);
                } else {
                    return null;
                }
                break;
            case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                // switchMechanism is always null
                param = parseICommandInitializer(null);
                break;
        }

        return param;
    }

    private ICommandInitializer parseICommandInitializer(Iterable<IByWeb> switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
