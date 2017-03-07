package aeon.core.command_execution.commands.initialization;

import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework_abstraction.controls.Control;
import aeon.core.framework_abstraction.drivers.IDriver;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Command Initializer.
 */
public interface ICommandInitializer {
    Consumer<IDriver> SetContext(UUID guid);

    Control FindElement(UUID guid, IDriver driver, IBy selector);
}
