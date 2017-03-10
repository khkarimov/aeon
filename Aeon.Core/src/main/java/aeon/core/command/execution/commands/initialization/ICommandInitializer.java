package aeon.core.command.execution.commands.initialization;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;

/**
 * Command Initializer.
 */
public interface ICommandInitializer {
    // Should we change this method name now that
    // we don't actually accept a GUID?
    Consumer<IDriver> SetContext();

    Control FindElement(IDriver driver, IBy selector);
}
