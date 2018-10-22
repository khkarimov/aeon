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
import java.util.Map;

/**
 * Web product extension.
 */
@Extension
public class WebProductTypeExtension implements IProductTypeExtension {

    String commandPackage = "aeon.core.command.execution.commands.web.";

    @Override
    public IBy createSelector(Map<String, String> selector) {
        String value = selector.get("value");
        String type = selector.get("type");

        if (value == null) {
            return null;
        }

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
    public Object createCommand(String commandString, List<Object> args) {
        Class<?> commandClass;

        try {
            commandClass = Class.forName(commandPackage + commandString);

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

        Object[] params = getParameters(args, parameters);

        try {
            return commandConstructor.newInstance(params);
        } catch (Exception e) {
            return null;
        }
    }

    private Object[] getParameters(List<Object> args, Class[] parameters) {
        Object[] params = new Object[0];

        if (args != null) {
            params = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                params[i] = createParameter(parameters, args, i);
            }
        }

        return params;
    }

    private Object createParameter(Class[] parameters, List<Object> args, int i) {
        Object param;

        switch (parameters[i].getName()) {
            case "aeon.core.common.web.interfaces.IByWeb":
                if (Map.class.isAssignableFrom(args.get(i).getClass())) {
                    param = createSelector((Map) args.get(i));
                } else {
                    return null;
                }
                break;
            case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                // switchMechanism is always null
                param = parseICommandInitializer(null);
                break;
            default:
                param = args.get(i);
        }

        return param;
    }

    private ICommandInitializer parseICommandInitializer(Iterable<IByWeb> switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
