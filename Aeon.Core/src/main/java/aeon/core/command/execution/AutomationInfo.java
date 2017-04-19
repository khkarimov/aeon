package aeon.core.command.execution;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;

/**
 * Provides access to data required for automation.
 */
public class AutomationInfo {
    private Configuration configuration;
    private ICommandExecutionFacade commandExecutionFacade;
    private IAdapter adapter;
    private IDriver driver;

    /**
     * Creates a new {@link AutomationInfo} instance.
     *
     * @param configuration
     * @param driver
     * @param adapter
     */
    public AutomationInfo(Configuration configuration, IDriver driver, IAdapter adapter) {
        this.driver = driver;

        this.adapter = adapter;
        this.configuration = configuration;
    }

    public final IAdapter getAdapter() {
        return this.adapter;
    }

    public final void setAdapter(IAdapter adapter) {
        this.adapter = adapter;
    }

    public final ICommandExecutionFacade getCommandExecutionFacade() {
        return commandExecutionFacade;
    }

    public final void setCommandExecutionFacade(ICommandExecutionFacade value) {
        commandExecutionFacade = value;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public IDriver getDriver() {
        return driver;
    }

    public void setDriver(IDriver driver) {
        this.driver = driver;
    }
}
