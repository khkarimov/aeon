package echo.core.framework_abstraction.webdriver;

import com.sun.glass.ui.Size;
import echo.core.common.exceptions.*;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.WebElement;

import java.net.URL;
import java.util.*;
import java.util.NoSuchElementException;

/**
 * Defines the interface through which the user controls the browser.
 */
public interface IWebDriverAdapter extends IDriver {
}