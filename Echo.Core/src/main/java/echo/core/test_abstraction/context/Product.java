package echo.core.test_abstraction.context;

import echo.core.command_execution.AutomationInfo;
import echo.core.framework_abstraction.Driver;
import echo.core.framework_abstraction.webdriver.IWebDriverAdapter;
import echo.core.framework_abstraction.webdriver.IWebDriverFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by DionnyS on 4/12/2016.
 */
public class Product<T extends Driver> {
    private AutomationInfo automationInfo;

    public Product() {
        String factory = this.createFactory().GetFactory();
        //this.automationInfo = new AutomationInfo();
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

//    public void Launch() {
//        T factory = this.createFactory();
//
//        this.automationInfo = new AutomationInfo(factory.getDriver());
//    }

    private T createFactory() {
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
