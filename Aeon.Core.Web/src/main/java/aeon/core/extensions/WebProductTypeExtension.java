package aeon.core.extensions;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.common.interfaces.IBy;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;
import org.pf4j.Extension;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Web product extension.
 */
@Extension
public class WebProductTypeExtension implements IProductTypeExtension {

    String commandPackage = "aeon.core.command.execution.commands.web.";

    @Override
    public WebCommandExecutionFacade createCommandExecutionFacade(AutomationInfo automationInfo) {
        Configuration configuration = automationInfo.getConfiguration();

        long timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);
        long throttle = (long) configuration.getDouble(Configuration.Keys.THROTTLE, 100);
        long ajaxTimeout = (long) configuration.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        DelegateRunnerFactory delegateRunnerFactory = new DelegateRunnerFactory(Duration.ofMillis(throttle), Duration.ofSeconds(timeout));
        AjaxWaiter ajaxWaiter = new AjaxWaiter(automationInfo.getDriver(), Duration.ofSeconds(ajaxTimeout));

        WebCommandExecutionFacade commandExecutionFacade = new WebCommandExecutionFacade(delegateRunnerFactory, ajaxWaiter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        return commandExecutionFacade;
    }

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

        if (args == null) {
            return params;
        }

        params = new Object[parameters.length];

        int i = 0;
        int j = 0;

        for (Class p : parameters) {
            switch (p.getName()) {
                case "aeon.core.common.web.interfaces.IByWeb":
                    if (Map.class.isAssignableFrom(args.get(i).getClass())) {
                        params[i] = createSelector((Map) args.get(j));
                    } else {
                        return null;
                    }
                    break;
                case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                    // switchMechanism is always null
                    params[i] = parseICommandInitializer(null);
                    j--;
                    break;
                case "aeon.core.common.web.WebSelectOption":
                    params[i] = WebSelectOption.valueOf((String) args.get(j));
                    break;
                default:
                    params[i] = args.get(j);
            }

            i++;
            j++;
        }

        return params;
    }

    private ICommandInitializer parseICommandInitializer(IByWeb... switchMechanism) {
        return new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), switchMechanism);
    }
}
