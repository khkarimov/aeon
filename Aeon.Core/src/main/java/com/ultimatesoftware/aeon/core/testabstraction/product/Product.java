package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.AeonConfigKey;
import com.ultimatesoftware.aeon.core.common.exceptions.AeonLaunchException;

/**
 * Abstract class for Product implementation.
 */
public abstract class Product {

    protected final AutomationInfo automationInfo;
    protected final Configuration configuration;

    /**
     * Initializes a product.
     *
     * @param automationInfo The automation info object containing the driver and the configuration.
     */
    public Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        this.configuration = automationInfo.getConfiguration();
    }

    /**
     * Returns the AutomationInfo which includes the configuration, the driver and  the adapter.
     *
     * @return the AutomationInfo of the Product.
     */
    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    /**
     * Switches to another product by passing the {@link AutomationInfo} object along.
     *
     * @param productClass The new product's class
     * @param <T>          The type of the new product.
     * @return The newly instantiated product.
     */
    public <T extends Product> T attach(Class<T> productClass) {

        T product;
        try {
            product = productClass
                    .getDeclaredConstructor(AutomationInfo.class)
                    .newInstance(this.automationInfo);
        } catch (Exception e) {
            throw new AeonLaunchException(e);
        }

        return product;
    }

    /**
     * Is called in case launching fails.
     * Can be used for clean up logic.
     *
     * @param e Exception that caused the failure.
     */
    void onLaunchFailure(Exception e) {
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
