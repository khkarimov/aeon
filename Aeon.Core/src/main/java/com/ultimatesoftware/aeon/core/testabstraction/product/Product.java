package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.common.Capability;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapterExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;

/**
 * Abstract class for Product implementation.
 */
public abstract class Product {

    protected AutomationInfo automationInfo;
    protected Configuration configuration;

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
    void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    /**
     * Abstract for launch function.
     *
     * @param plugin The IAdapterExtension to be added.
     * @throws InstantiationException If an instantiation exception is made.
     * @throws IllegalAccessException If issue obtaining keys.
     */
    protected void launch(IAdapterExtension plugin) throws InstantiationException, IllegalAccessException {
        IDriver driver;
        IAdapter adapter;

        adapter = createAdapter(plugin);

        driver = (IDriver) configuration.getDriver().newInstance();
        driver.configure(adapter, configuration);

        this.automationInfo = new AutomationInfo(configuration, driver, adapter);

        afterLaunch();
    }

    /**
     * Switches to another product by passing the {@link AutomationInfo} object along.
     *
     * @param productClass The new product's class
     * @param <T>          The type of the new product.
     * @return The newly instantiated product.
     */
    public <T extends Product> T switchTo(Class<T> productClass) {

        T newProduct;
        try {
            newProduct = productClass.newInstance();
        } catch (Exception e) {
            throw new AeonLaunchException(e);
        }
        newProduct.setAutomationInfo(this.getAutomationInfo());
        newProduct.afterLaunch();

        return newProduct;
    }

    /**
     * Is called in case launching fails.
     * Can be used for clean up logic.
     *
     * @param e Exception that caused the failure.
     */
    protected void onLaunchFailure(Exception e) {
    }

    /**
     * Create and returns and IAdapter given a plugin.
     *
     * @param plugin The Product's plugin to be returned.
     * @return The plugin with a newly created adapter.
     */
    private IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    /**
     * Returns the configuration of the Product.
     *
     * @return The configuration of the product.
     */
    Configuration getConfiguration() {
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
     * Default afterLaunch function.
     */
    protected void afterLaunch() {
        automationInfo.launched();
    }

    /**
     * Returns a boolean based on a key and a defaultValue of the configuration.
     *
     * @param key          The key to be compared.
     * @param defaultValue The default boolean to be compared.
     * @return the truth of the comparison.
     */
    public boolean getConfig(AeonConfigKey key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    /**
     * Returns a boolean based on a key and a defaultValue of the configuration.
     *
     * @param key          The key value string to be compared.
     * @param defaultValue The default boolean to be compared.
     * @return the truth of the comparison.
     */
    public boolean getConfig(String key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    /**
     * Returns a string based on a key and a defaultValue of the configuration.
     *
     * @param key          The key to be compared.
     * @param defaultValue The default string to be compared.
     * @return the string of the configuration.
     */
    public String getConfig(AeonConfigKey key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    /**
     * Returns a string based on a key and a defaultValue of the configuration.
     *
     * @param key          The key value string to be compared.
     * @param defaultValue The default string to be compared.
     * @return the string of the configuration.
     */
    public String getConfig(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    /**
     * Returns a double based on a key and a defaultValue of the configuration.
     *
     * @param key          The key to be compared.
     * @param defaultValue The default string to be compared.
     * @return the double of the configuration.
     */
    public double getConfig(AeonConfigKey key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }

    /**
     * Returns a double based on a key and a defaultValue of the configuration.
     *
     * @param key          The key value string to be compared.
     * @param defaultValue The default string to be compared.
     * @return the double of the configuration.
     */
    public double getConfig(String key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }
}
