package aeon.core.common.helpers;

import aeon.core.common.exceptions.ScriptExecutionException;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                injectJS();
                return;
            }
            Sleep.waitInternal();
        }while(count != 0  && clock.getUtcNow().isBefore(end.toInstant()));
    }

    //JSONP needs to be injected first cause it declares the aeon variable used for the scripts.
    public void injectJS() {
        try {
            String InjectScriptTag = "var a = document.createElement('script');a.text=\"" +
                    getJSONPWaiterJS() + "\";a.setAttribute('id', 'aeon.JSONP.waiter.script');document.body.appendChild(a);";
            webDriver.executeScript(InjectScriptTag);
            InjectScriptTag = "var a = document.createElement('script');a.text=\"" +
                    getAjaxWaiterJS() + "\";a.setAttribute('id', 'aeon.ajax.waiter.script');document.body.appendChild(a);";
            webDriver.executeScript(InjectScriptTag);
            webDriver.executeScript("aeon.ajaxTimeout = " + (timeout.getMillis() - 2000));
            System.out.println("Injected JS");
        }catch (ScriptExecutionException e){
            System.out.println("Could not inject JS");
        }
    }

    public String getJSONPWaiterJS() {
        File file = new File("../JSONPWaiter.js");
        try {
            byte[] data = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
            return new String(data);
        } catch (FileNotFoundException e) {
            System.out.println("File not found on path");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem reading from file");
            e.printStackTrace();
        }
        return "";
    }
    public String getAjaxWaiterJS() {
        File file = new File("../AjaxWaiter.js");
        try {
            byte[] data = Files.readAllBytes(Paths.get(file.getCanonicalPath()));
            return new String(data);
        } catch (FileNotFoundException e) {
            System.out.println("File not found on path");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Problem reading from file");
            e.printStackTrace();
        }
        return "";
    }
}
