package echo.core.framework_abstraction;

import com.sun.glass.ui.Size;
import echo.core.common.exceptions.*;
import echo.core.common.logging.ILog;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.webdriver.ICookieAdapter;
import echo.core.framework_abstraction.webdriver.IWebElementAdapter;
import echo.core.framework_abstraction.webdriver.IWebSelectElementAdapter;
import echo.core.test_abstraction.settings.ISettingsProvider;
import echo.core.test_abstraction.webenvironment.Parameters;

import java.net.URL;
import java.util.*;
import java.util.NoSuchElementException;

/**
 * Created by DionnyS on 4/13/2016.
 */
public interface IDriverFactory {
    IDriver createDriver(Parameters parameters, ILog log);
    ISettingsProvider getSettingsProvider();
}