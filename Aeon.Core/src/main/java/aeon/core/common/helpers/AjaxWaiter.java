package aeon.core.common.helpers;

import aeon.core.common.exceptions.ScriptExecutionException;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Duration;


/**
 * Created by JustinP on 6/2/2016.
 */
public class AjaxWaiter {
    private IWebDriver webDriver;
    private IClock clock;
    private Duration timeout;

    public AjaxWaiter(IDriver driver, Duration timeout){
        this.webDriver = (IWebDriver)driver;
        this.clock = new Clock();
        this.timeout = timeout;
    }

    public void setWebDriver(IWebDriver webDriver){
        this.webDriver = webDriver;
    }

    public IWebDriver getWebDriver(){
        return this.webDriver;
    }

    public void setTimeout(long millis){
        this.timeout = Duration.millis(millis);
    }

    public long getTimeout(){
        return timeout.getMillis();
    }

    public void waitForAsync() {
        long count;
        DateTime end = clock.getUtcNow().withDurationAdded(timeout.getMillis(), 1);
        do{
            try {
                count = (long) webDriver.executeScript("return aeon.ajaxCounter;");
            }catch(ScriptExecutionException e){
                return;
            }
            Sleep.waitInternal();
        }while(count != 0  && clock.getUtcNow().isBefore(end.toInstant()));
    }

}
