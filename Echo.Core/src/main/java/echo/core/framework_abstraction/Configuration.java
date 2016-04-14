package echo.core.framework_abstraction;

import echo.core.common.BrowserType;
import echo.core.common.logging.ILog;

/**
 * Created by DionnyS on 4/14/2016.
 */
public class Configuration {
    private Class driver;
    private Class adapter;
    private BrowserType browserType;
    private ILog log;

    public <D extends IDriver, A extends IAdapter> Configuration(Class<D>driver, Class<A> adapter) {
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

    public ILog getLog() {
        return log;
    }

    public void setLog(ILog log) {
        this.log = log;
    }
}
