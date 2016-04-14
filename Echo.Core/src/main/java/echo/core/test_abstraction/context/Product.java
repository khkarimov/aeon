package echo.core.test_abstraction.context;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.Configuration;
import echo.core.framework_abstraction.IAdapter;
import echo.core.framework_abstraction.IDriver;
import echo.core.test_abstraction.webenvironment.Parameters;

import java.lang.reflect.ParameterizedType;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Product {
    private AutomationInfo automationInfo;
    private Parameters parameters;
    private ILog log;
    private Configuration configuration;

    public Product(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    protected void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    protected void launch() {
        IDriver driver;
        IAdapter adapter;

        try {
            adapter = (IAdapter)configuration.getAdapter().newInstance();
            adapter = adapter.Configure(configuration);

            driver = (IDriver)configuration.getDriver().newInstance();
            driver.Configure(adapter);

            this.automationInfo = new AutomationInfo(parameters, driver, adapter, configuration.getLog());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }

    protected void switchDriver(IAdapter adapter) {
        this.automationInfo.setAdapter(adapter);
    }

    protected void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

//    protected ISettingsProvider getSettingsProvider() {
//        return factory.getSettingsProvider();
//    }

    protected void setLog(ILog log) {
        this.log = log;
    }
}
