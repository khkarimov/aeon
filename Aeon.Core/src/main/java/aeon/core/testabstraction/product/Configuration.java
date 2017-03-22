package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IWebDriver;

/**
 * Created By DionnyS on 4/14/2016.
 */
public class Configuration {
    private Class driver;
    private Class adapter;
    private BrowserType browserType;

    public <D extends IWebDriver, A extends IAdapter> Configuration(Class<D> driver, Class<A> adapter) {
        this.driver = driver;
        this.adapter = adapter;
    }

    public Class getDriver() {
        return driver;
    }

    public void setDriver(Class driver) {
        this.driver = driver;
    }

    public Class getAdapter() {
        return adapter;
    }

    public void setAdapter(Class adapter) {
        this.adapter = adapter;
    }

    public BrowserType getBrowserType() {
        return browserType;
    }

    public void setBrowserType(BrowserType browserType) {
        this.browserType = browserType;
    }
}
