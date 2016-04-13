package echo.core.test_abstraction.context;

import echo.core.command_execution.AutomationInfo;
import echo.core.framework_abstraction.Driver;

import java.lang.reflect.ParameterizedType;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Product<T extends Driver> {
    private AutomationInfo automationInfo;

    public Product() {
        String factory = this.createDriver().GetFactory();
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

    private T createDriver() {
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
