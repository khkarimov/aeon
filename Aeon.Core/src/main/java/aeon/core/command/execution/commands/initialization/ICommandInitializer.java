package aeon.core.command.execution.commands.initialization;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;

/**
 * Command Initializer.
 */
public interface ICommandInitializer {

    // TODO: Delete/change the name of this method.
    Consumer<IDriver> setContext();

    Control findElement(IDriver driver, IBy selector);
}
