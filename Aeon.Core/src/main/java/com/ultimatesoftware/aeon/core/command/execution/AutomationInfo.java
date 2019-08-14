package com.ultimatesoftware.aeon.core.command.execution;

import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.AeonTestExecution;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;

import java.awt.*;

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

        AeonTestExecution.startUp(configuration);
    }

    /**
     * Gets the {@link ICommandExecutionFacade }.
     *
     * @return the {@link ICommandExecutionFacade }
     */
    public ICommandExecutionFacade getCommandExecutionFacade() {
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
     * Gets the {@link IDriver }.
     *
     * @return the {@link IDriver}
     */
    public IDriver getDriver() {
        return driver;
    }

    /**
     * Method to indicate a successful launch of a product.
     */
    public void launched() {
        AeonTestExecution.launched(configuration, adapter);
    }

    /**
     * Method to indicate the successful completion of a test.
     */
    public void testSucceeded() {
        AeonTestExecution.testSucceeded();
    }

    /**
     * Method to indicate the end of a test due to a failure.
     *
     * @param message The failure message.
     */
    public void testFailed(String message) {
        AeonTestExecution.testFailed(message);
    }

    /**
     * Method to indicate the end of a test due to a failure.
     *
     * @param message The failure message.
     * @param e       The exception of the failure.
     */
    public void testFailed(String message, Exception e) {
        AeonTestExecution.testFailed(message, e);
    }

    /**
     * Method to indicate that a screenshot was taken.
     *
     * @param screenshot The screenshot that was taken.
     */
    public void screenshotTaken(Image screenshot) {
        AeonTestExecution.executionEvent("screenshotTaken", screenshot);
    }
}
