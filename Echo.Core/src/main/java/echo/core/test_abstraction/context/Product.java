package echo.core.test_abstraction.context;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.logging.ILog;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IDriverFactory;
import echo.core.test_abstraction.settings.ISettingsProvider;
import echo.core.test_abstraction.webenvironment.Parameters;

import java.lang.reflect.ParameterizedType;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Product<T extends IDriverFactory> {
    private AutomationInfo automationInfo;
    private Parameters parameters;
    private ILog log;
    private IDriverFactory factory;

    public Product() {
        this.factory = createDriverFactory();
    }

    public Product(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    public AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    public void setAutomationInfo(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
    }

    public void launch() {
        IDriver driver = factory.createDriver(parameters, log);
        setAutomationInfo(new AutomationInfo(driver, log));
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public ISettingsProvider getSettingsProvider() {
        return factory.getSettingsProvider();
    }

    public void setLog(ILog log) {
        this.log = log;
    }

    private T createDriverFactory() {
        try {
            ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class factoryClass = (Class) type.getActualTypeArguments()[0];

            return (T) factoryClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
