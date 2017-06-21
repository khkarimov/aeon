package aeon.core.testabstraction.product;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;

/**
 * Created by DionnyS on 4/12/2016.
 */
public abstract class Product {

    protected AutomationInfo automationInfo;
    protected Configuration configuration;
    protected WebCommandExecutionFacade commandExecutionFacade;

    /**
     * Empty Constructor.
     */
    public Product() {
    }

    /**
     * Constructor for Product using automationInfo.
     *
     * @param automationInfo The automationInfo to be added.
     */
    protected Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    /**
     * Gets the capability type from a Capability enum.
     *
     * @return A capability enum.
     */
    public abstract Capability getRequestedCapability();

    /**
     * Returns the AutomationInfo which includes the configuration, idriver, and iadapter.
     *
     * @return the AutomationInfo of the Product.
     */
    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    /**
     * Sets the AutomationInfo of the Product.
     *
     * @param automationInfo The AutomationInfo to be set.
     */
    protected void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    /**
     * Abstract for launch function.
     *
     *
     * @param plugin The IAdapterExtension to be added.
     * @throws InstantiationException If an instantiation exception is made.
     * @throws IllegalAccessException If issue obataining keys.
     *
     */
    protected abstract void launch(IAdapterExtension plugin) throws InstantiationException, IllegalAccessException;

    /**
     * Create and returns and IAdapter given a plugin.
     *
     * @param   plugin  The Product's plugin to be returned.
     * @return          The plugin with a newly created adapter.
     */
    protected IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    /**
     * Returms the configuration of the Product.
     *
     * @return The configration of the product.
     */
    protected Configuration getConfiguration() {
        return this.configuration;
    }

    /**
     * Sets the configuration of the Product.
     *
     * @param configuration The configuration to be set.
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Sets the adapter of the Product's automationInfo.
     *
     * @param adapter The adapter to be set.
     */
    protected void switchDriver(IAdapter adapter) {
        this.automationInfo.setAdapter(adapter);
    }

    /**
     * Default afterLaunch function.
     */
    protected void afterLaunch() {
    }

    /**
     * Returns a boolean based on a key and a defaultValue of the configuration.
     *
     * @param key The key to be compared.
     * @param defaultValue The default boolean to be compared.
     * @return the truth of the comparison.
     */
    public boolean getConfig(String key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    /**
     * Returns a string based on a key and a defaultValue of the configuration.
     *
     * @param key The key to be compared.
     * @param defaultValue The default string to be compared.
     * @return the string of the configuration.
     */
    public String getConfig(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    /**
     * Returns a double based on a key and a defaultValue of the configuration.
     *
     * @param key The key to be compared.
     * @param defaultValue The default string to be compared.
     * @return the double of the configuration.
     */
    public double getConfig(String key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }
}
