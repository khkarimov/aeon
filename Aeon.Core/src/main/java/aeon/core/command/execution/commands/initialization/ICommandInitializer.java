package aeon.core.command.execution.commands.initialization;

import aeon.core.common.interfaces.IBy;
import aeon.core.framework.abstraction.controls.Control;
import aeon.core.framework.abstraction.drivers.IDriver;

import java.util.function.Consumer;

/**
 * Command Initializer interface.
 */
public interface ICommandInitializer {

    // TODO(johnDoe): Delete/change the name of this method.

    /**
     * Sets the consumer for the command initializer.
     *
     * @return The {@link Consumer} of the command initializer.
     */
    Consumer<IDriver> setContext();

    /**
     * Finds a Control element using an IDriver.
     *
     * @param driver The framework abstraction facade.
     * @param selector The selector for the Element.
     *
     * @return The {@link Control} of the found element
     */
    Control findElement(IDriver driver, IBy selector);
}
