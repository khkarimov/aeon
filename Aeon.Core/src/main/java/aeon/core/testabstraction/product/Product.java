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

    public Product() {
    }

    protected Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    public abstract Capability getRequestedCapability();

    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    protected void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected abstract void launch(IAdapterExtension plugin) throws InstantiationException, IllegalAccessException;

    protected IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    protected void switchDriver(IAdapter adapter) {
        this.automationInfo.setAdapter(adapter);
    }


    protected void afterLaunch() {
    }

    public boolean getConfig(String key, boolean defaultValue) {
        return configuration.getBoolean(key, defaultValue);
    }

    public String getConfig(String key, String defaultValue) {
        return configuration.getString(key, defaultValue);
    }

    public double getConfig(String key, double defaultValue) {
        return configuration.getDouble(key, defaultValue);
    }
}
