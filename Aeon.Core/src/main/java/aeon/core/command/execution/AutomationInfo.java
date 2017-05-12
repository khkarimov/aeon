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
     * Creates a new instance of {@link AutomationInfo}.
     *
     * @param configuration The configuration in use.
     * @param driver        The driver for the current product.
     * @param adapter       The adapter for the current product.
     */
    public AutomationInfo(Configuration configuration, IDriver driver, IAdapter adapter) {
        this.driver = driver;
        this.adapter = adapter;
        this.configuration = configuration;
    }

    /**
     * Gets the {@link IAdapter }.
     *
     * @return the {@link IAdapter }
     */
    public final IAdapter getAdapter() {
        return this.adapter;
    }

    /**
     * Sets the {@link IAdapter }.
     *
     * @param adapter the new {@link IAdapter }
     */
    public final void setAdapter(IAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Gets the {@link ICommandExecutionFacade }.
     *
     * @return the {@link ICommandExecutionFacade }
     */
    public final ICommandExecutionFacade getCommandExecutionFacade() {
        return commandExecutionFacade;
    }

    /**
     * Sets the {@link ICommandExecutionFacade }.
     *
     * @param value the new {@link ICommandExecutionFacade }
     */
    public final void setCommandExecutionFacade(ICommandExecutionFacade value) {
        commandExecutionFacade = value;
    }

    /**
     * Gets {@link Configuration}.
     *
     * @return the {@link Configuration }
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Sets the {@link Configuration}.
     *
     * @param configuration the new {@link Configuration}
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets the {@link IDriver }.
     *
     * @return the {@link IDriver}
     */
    public IDriver getDriver() {
        return driver;
    }

    /**
     * Sets the {@link IDriver }.
     *
     * @param driver the new {@link IDriver }
     */
    public void setDriver(IDriver driver) {
        this.driver = driver;
    }
}
