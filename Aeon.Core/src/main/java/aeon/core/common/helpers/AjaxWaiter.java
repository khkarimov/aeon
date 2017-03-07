package aeon.core.common.helpers;

import aeon.core.common.exceptions.ScriptExecutionException;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.UUID;

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

    public void InjectJS(UUID guid) {
        try {
            String InjectScriptTag = "var a = document.createElement('script');a.text=\"" +
                    getAjaxWaiterJS() + "\";a.setAttribute('id', 'aeon.ajax.waiter.script');document.body.appendChild(a);";
            webDriver.ExecuteScript(guid, InjectScriptTag);
            System.out.println("Injected JS");
        }catch (ScriptExecutionException e){
            System.out.println("Could not inject JS");
        }
    }

    public void WaitForAsync(UUID guid) {
        long count;
        DateTime end = clock.getUtcNow().withDurationAdded(timeout.getMillis(), 1);
        do{
            try {
                count = (long) webDriver.ExecuteScript(guid, "return aeonAjaxCounter");
                System.out.println("COUNT: " + count);
            }catch(ScriptExecutionException e){
                InjectJS(guid);
                return;
            }
            Sleep.WaitInternal();
        }while(count != 0  && clock.getUtcNow().isBefore(end.toInstant()));
    }

    private final static String getAjaxWaiterJS(){
        return  "var aeonAjaxCounter = 0;\n" +
                "(function(open) {\n" +
                "            var asyncCallOccurred = false;\n" +
                "            var event;\n" +
                "\n" +
                "            if (document.createEvent) {\n" +
                "                event = document.createEvent('HTMLEvents');\n" +
                "                event.initEvent('aeon.async.done', true, true);\n" +
                "            } else {\n" +
                "                event = document.createEventObject();\n" +
                "                event.eventType = 'aeon.async.done';\n" +
                "            }\n" +
                "\n" +
                "            event.eventName = 'aeon.async.done';\n" +
                "\n" +
                "            document.getElementById('start').addEventListener('aeon.asyc.done', function() {\n" +
                "                document.getElementById('start').setAttribute('data-async', 'done');\n" +
                "            });\n" +
                "\n" +
                "            XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {\n" +
                "                $('#counter').html(++aeonAjaxCounter);\n" +
                "\n" +
                "                var readyStateChange = function() {\n" +
                "                    if (this.readyState === 4) {\n" +
                "                        $('#counter').html(--aeonAjaxCounter);\n" +
                "\n" +
                "                        if(aeonAjaxCounter === 0) {\n" +
                "                            setTimeout(function() {\n" +
                "                                if (document.createEvent) {\n" +
                "                                    document.getElementById('start').dispatchEvent(event);\n" +
                "                                } else {\n" +
                "                                    document.getElementById('start').fireEvent('on' + event.eventType, event);\n" +
                "                                }\n" +
                "                            }, 1);\n" +
                "                        }\n" +
                "                    }\n" +
                "                };\n" +
                "\n" +
                "                this.addEventListener('readystatechange', readyStateChange, false);\n" +
                "\n" +
                "                open.call(this, method, url, async, user, pass);\n" +
                "            };\n" +
                "\n" +
                "        })(XMLHttpRequest.prototype.open);";}
}
