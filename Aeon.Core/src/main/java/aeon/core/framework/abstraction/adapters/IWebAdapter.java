package aeon.core.framework.abstraction.adapters;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.AppRuntime;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import com.sun.glass.ui.Size;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;

public interface IWebAdapter extends IAdapter {

    /**
     * Gets the title of the current browser window.
     *
     * @return The title of the current browser window.
     */
    String getTitle();

    /**
     * Gets the URL the browser is currently displaying.
     *
     * @return The URL the browser is currently displaying.
     */
    URL getUrl();

    /**
     * Gets the current window handle, which is an opaque handle to this window that uniquely identifies it within this driver instance.
     *
     * @return The current window handle, which is an opaque handle to this window that uniquely identifies it within this driver instance.
     */
    String getCurrentWindowHandle();

    /**
     * Gets the window handles of open browser windows.
     *
     * @return The window handles of open browser windows.
     */
    Collection<String> getWindowHandles();

    /**
     * Load a new web page in the current browser window.
     *
     * @param url The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If URL is null.
     */
    String goToUrl(URL url);

    /**
     * Scrolls to the top of the window.
     */
    void scrollToTop();

    /**
     * Scrolls to the end of the window.
     */
    void scrollToEnd();

    /**
     * Finds the first Element using the given mechanism.
     *
     * @param findBy The locating mechanism to use.
     * @return The first matching Element on the current product.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebControl findElement(IBy findBy);

    /**
     * Finds all Elements within the current product
     * using the given mechanism.
     *
     * @param findBy The locating mechanism to use.
     * @return A ReadOnly Collection of found elements.
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<WebControl> findElements(IBy findBy);

    /**
     * Finds the first Select Element using the given mechanism.
     *
     * @param findBy The locating mechanism to use.
     * @return The first matching Select Element on the current product.
     * @throws IllegalArgumentException                 If FindBy is null.
     * @throws NoSuchElementException                   If there is no such element.
     * @throws ScriptReturnValueNotHtmlElementException If the JavaScript did not return an HTML element.
     * @throws UnsupportedSelectElementException        If the select element is not supported.
     */
    WebControl findSelectElement(IBy findBy);

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     */
    void switchToDefaultContent();

    /**
     * Selects the first frame using the given method.
     *
     * @param findBy The locating mechanism to use.
     * @throws IllegalArgumentException    If FindBy is null.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void switchToFrame(IBy findBy);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param windowTitle The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If windowTitle is null.
     * @throws IllegalArgumentException If windowTitle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String switchToWindowByTitle(String windowTitle);

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param handle The handle of the window to select.
     * @throws IllegalArgumentException If handle is null.
     * @throws IllegalArgumentException If handle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void switchToWindowByHandle(String handle);

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param value The URL to which to switch focus.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If value is null.
     * @throws IllegalArgumentException If value is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String switchToWindowByUrl(String value);

    /**
     * close the current window, quitting the browser if it is the last window currently open.
     */
    void close();

    /**
     * Quits this driver, closing every associated window.
     */
    void quit();

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @throws NoAlertException If the alert does not exist.
     */
    void verifyAlertExists();

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @throws AlertExistsException If the alert exists.
     */
    void verifyAlertNotExists();

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @return The value of the text in the alert.
     */
    String getAlertText();

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param keysToSend The keystrokes to send.
     */
    void sendKeysToAlert(String keysToSend);

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     */
    void acceptAlert();

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     */
    void dismissAlert();

    /**
     * Focuses the currently selected window.
     */
    void focusWindow();

    /**
     * Executes JavaScript in the product of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object executeScript(String script, Object... args);

    /**
     * Move back a single entry in the browser's history.
     */
    void back();

    /**
     * Move a single "item" forward in the browser's history.
     *
     * Does nothing if we are on the latest page viewed.
     */
    void forward();

    /**
     * Refreshes the current page.
     */
    void refresh();

    /**
     * Maximizes the current window if it is not already maximized.
     */
    void maximize();

    /**
     * Resizes the current window.
     *
     * @param size The new browser size.
     */
    void resize(Size size);

    /**
     * Opens on a type input type=file.
     *
     * @param selector The selector for the element.
     */
    void openFileDialog(IBy selector);

    /**
     * Types keys of file indicated by provided path
     * REQUIRES DIALOG BOX TO ALREADY BE OPENED BY openFileDialog.
     *
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void selectFileDialog(IBy selector, String path);

    /**
     * Opens a windows select file dialog and selects
     * file indicated by provided path.
     *
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void uploadFileDialog(IBy selector, String path);

    /**
     * Scrolls to the element on the page if in Chrome.
     *
     * @param element The selector.
     */
    void scrollElementIntoView(WebControl element);

    /**
     * Gets a screenshot of the currently focus browser window.
     *
     * @return Image of the browser window.
     */
    Image getScreenshot();

    /**
     * Gets the source of the current browser window.
     *
     * @return The current page's source as a string.
     */
    String getPageSource();

    /**
     * Drags one element and drops it into another.
     *
     * @param dropElement   The element to drop.
     * @param targetElement The target element.
     */
    void dragAndDrop(WebControl dropElement, IBy targetElement);

    /**
     * Right clicks an element.
     *
     * @param element The element to right click.
     */
    void rightClick(WebControl element);

    /**
     * Clicks an element.
     *
     * @param element The web element to click.
     */
    void click(WebControl element);

    /**
     * Double clicks an element.
     *
     * @param element The web element to double click.
     */
    void doubleClick(WebControl element);

    /**
     * Holds a click on an element for the duration specified.
     *
     * @param element  The web element to click.
     * @param duration click for at least this long (in milliseconds).
     */
    void clickAndHold(WebControl element, int duration);

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param element The web element to click.
     * @param x       The x offset.
     * @param y       The y offset.
     */
    void clickAtOffset(WebControl element, int x, int y);

    /**
     * Refreshes a Frame.
     */
    void refreshFrame();

    /**
     * Gets the list of all cookies.
     *
     * @return The list of cookies.
     */
    java.util.List<IWebCookie> getAllCookies();

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    IWebCookie getCookie(String name);

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    void addCookie(IWebCookie cookie);

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be modified.
     */
    void deleteCookie(String name);

    /**
     * Deletes all cookies.
     */
    void deleteAllCookies();

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    void modifyCookie(String name, String value);

    /**
     * Gets the currently focused element.
     *
     * @return The currently focused element.
     */
    WebControl getFocusedElement();

    /**
     * Gets the HTML Tag type of the given element.
     *
     * @param element The element to get the Tag of.
     * @return The type of Tag.
     */
    String getElementTagName(WebControl element);

    /**
     * Clears the text of the given element.
     *
     * @param element The element to be cleared.
     */
    void clearElement(WebControl element);

    /**
     * Choose a Select Element by its Value.
     *
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void chooseSelectElementByValue(WebControl element, String value);

    /**
     * Choose a Select Element by its Text.
     *
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void chooseSelectElementByText(WebControl element, String value);

    /**
     * Clicks on the given element.
     *
     * @param element The element to be clicked.
     */
    void clickElement(WebControl element);

    /**
     * Sends Keys to the given element programmatically.
     *
     * @param element The element to receive the keys.
     * @param value   The keys to be sent to the element.
     */
    void sendKeysToElement(WebControl element, String value);

    /**
     * Gets the value of a given attribute of a given element.
     *
     * @param element The element to get the attribute values of.
     * @param value   The name of the attribute to get the value of.
     * @return The value of the element attribute
     */
    String getElementAttribute(WebControl element, String value);

    /**
     * Switches to the Main Window.
     *
     * @param mainWindowHandle              The handle of the main window
     * @param waitForAllPopupWindowsToClose Whether to wait for all popup windows to close.
     */
    void switchToMainWindow(String mainWindowHandle, Boolean waitForAllPopupWindowsToClose);

    /**
     * Blurs the current element, takes off focus.
     * A globally unique identifier associated with this call
     *
     * @param element The element to be blurred
     */
    void blur(WebControl element);

    /**
     * Checks a checkbox.
     *
     * @param element The checkbox to be checked.
     */
    void checkElement(WebControl element);

    /**
     * Unchecks a checkbox.
     *
     * @param element The checkbox to be unchecked.
     */
    void unCheckElement(WebControl element);

    /**
     * Checks that an element is enabled.
     *
     * @param element The web element to check.
     */
    void isElementEnabled(WebControl element);

    /**
     * Checks that an element is disabled.
     *
     * @param element The web element to check.
     */
    void isElementDisabled(WebControl element);

    /**
     * Checks to see if a selected element's checkbox is selected.
     *
     * @param element The select element.
     */
    void selected(WebControl element);

    /**
     * Checks to see if a selected element's checkbox is not selected.
     *
     * @param element The select element.
     */
    void notSelected(WebControl element);

    /**
     * Checks to see if a selected element is visible.
     *
     * @param element The select element.
     */
    void visible(WebControl element);

    /**
     * Checks to see if a selected element is hidden.
     *
     * @param element The select element.
     */
    void notVisible(WebControl element);

    /**
     * If this method was called then the element exists.
     * Logic done at command initialization.
     *
     * @param element The web element.
     */
    void exists(WebControl element);

    /**
     * If this method was reached then the element exists when it should not.
     * Logic done at command initialization.
     *
     * @param element The web element.
     */
    void notExists(WebControl element);

    /**
     * Asserts that a select element posseses all of the elements passed to it. It can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched by either their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the element should posses.
     * @param optgroup The optional option group that which will be searched.
     * @param select   The method by which the options will be searched either by value or by text.
     */
    void elementHasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element does not posses any of the options passed. Can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched for either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the select element should not have.
     * @param optgroup The optional option group that will be searched.
     * @param select   The method by which the options will be searched for either by visible text or their value.
     */
    void elementDoesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Moves the mouse cursor off of an element.
     *
     * @param element The element to Mouse Out of
     */
    void mouseOut(WebControl element);

    /**
     * Moves the mouse cursor on top of an element.
     *
     * @param element The element to Mouse Over
     */
    void mouseOver(WebControl element);

    /**
     * Asserts that the selected element's body tag will be changed into the provided String value.
     *
     * @param element by The selector.
     * @param value   Html to be inserted into body tag
     */
    void setBodyValueByJavaScript(WebControl element, String value);

    /**
     * Asserts that the selected element's value tag will be changed into the provided String value.
     *
     * @param element by The selector.
     * @param value   Html to be inserted into a value tag
     */
    void setTextByJavaScript(WebControl element, String value);

    /**
     * Sets the Value of a Div element by Javascript.
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void setDivValueByJavaScript(WebControl element, String value);

    /**
     * Clicks all elements that corresponding with the given IBy.
     *
     * @param elementsBy The selector.
     */
    void clickAllElements(IBy elementsBy);

    /**
     * Asserts that a select element not only has all of the options provided by that they are all in the order provided. Can optionally be passed an option group
     * that if non-null will be searched in isolation instead of the entire select. Options can be searched either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options to be searched for.
     * @param optgroup The visible text of the optional option group which would be searched.
     * @param select   The method by which the options will be searched, either by text or value.
     */
    void elementHasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element has a certain amount of options. Can optionally be passed an option group that if non-null will be searched
     * in isolation instead of the he entire select.
     *
     * @param element   The select element.
     * @param optnumber The amount of elements the select should have.
     * @param optgroup  The visible text of the optional option group which would be searched.
     */
    void hasNumberOfOptions(WebControl element, int optnumber, String optgroup);

    /**
     * Asserts that a select element has all of its options in a certain order, either ascending or descending alphanumerically by either their value
     * or their visible text.
     *
     * @param element  The select element.
     * @param compare  The method by which the options will be compared.
     * @param optGroup The optional option group which would be searched in isolation instead.
     */
    void hasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * Comparisons are made ignoring whitespace and case.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void hasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the chilren should not posses.
     * @param selector  The selector for the children to be searched.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void doesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text. Comparisons made ignoring case and whitespace.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the chilren should not posses.
     * @param selector  The selector for the children to be searched.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void doesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void hasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void is(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void isLike(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void isNotLike(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that the Text of given Alert is same as given string.
     *
     * @param comparingText String to compare against Alert Text.
     */
    void verifyAlertText(String comparingText);

    /**
     * Asserts that the Text of an Alert is like a given string(spacing and cases* ignored).
     * caseSensitive is an option
     *
     * @param comparingText String to compare against Alert Text.
     * @param caseSensitive Determine caseSensitive comparison
     */
    void verifyAlertTextLike(String comparingText, boolean caseSensitive);

    /**
     * Asserts the Title of a Page is equal to a given String
     * A globally unique identifier associated with this call.
     *
     * @param comparingTitle String to compare against Page Title.
     */
    void verifyTitle(String comparingTitle);

    /**
     * Asserts the URL is equal to the given URL
     * A globally unique identifier associated with this call.
     *
     * @param comparingURL The URL to compare against
     */
    void verifyURL(URL comparingURL);

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. has a
     * Margin of error. The date must be in the ISO-8601 standard.
     *
     * @param element       The element that posseses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expected      The expected date that the attribute should posses.
     * @param delta         The margin of error that the date can be within. Cannot posses any weeks, months or years due to
     *                      them having variable lengths.
     */
    void datesApproximatelyEqual(WebControl element, String attributeName, DateTime expected, Period delta);

    /**
     * Returns the enumerable AppRuntime representing the current browser.
     *
     * @return Returns the AppRuntime associated with this browser.
     */
    AppRuntime getAppRuntime();

    /**
     * Gets the bounding rectangle for an element.
     *
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    ClientRects getClientRects(WebControl element);

    /**
     * Sends a non-alphanumeric keys to an element.
     *
     * @param element The element to recieve the keys.
     * @param key     The key to be sent.
     */
    void pressKeyboardKey(WebControl element, KeyboardKey key);

    /**
     * Asserts a window with a given title does not exists. A globally unique identifier associated with the call.
     *
     * @param windowTitile The title of the window to search for.
     * @return The title of the window
     */
    String windowDoesNotExistByTitle(String windowTitile);

    /**
     * Asserts a window with a given URL does not exists. A globally unique identifier associated with the call.
     *
     * @param url The URL of the window to search for.
     * @return The URL of the window
     */
    String windowDoesNotExistByUrl(String url);

    /**
     * Sets the Text or Value of an element. A globally unique identifier associated with the call.
     *
     * @param element  The web element that is being modified.
     * @param option   Enum which determined whether to set tht Text or the Value.
     * @param setValue The new value for the Text or Value attribute of the control.
     */
    void set(WebControl element, WebSelectOption option, String setValue);


    /**
     * Sets the Username and Password for an authentication input or alert.
     *
     * @param setUsername The new value for the Username attribute of the control.
     * @param setPassword The new value for the Password attribute of the control.
     */
    void setAuthenticationCredentials(String setUsername, String setPassword);

    /**
     * Locks a mobile device.
     */
    void mobileLock();

    /**
     * Locks a mobile device.
     *
     * @param seconds The number of seconds that the device should remain locked (iOS only).
     */
    void mobileLock(int seconds);

    /**
     * Executes a swipe on a mobile device.
     *
     * @param startx Starting x coord.
     * @param starty Starting y coord.
     * @param endx Ending x coord.
     * @param endy Ending y coord.
     * @param duration The duration of the execution of the swipe
     */
    void mobileSwipe(int startx, int starty, int endx, int endy, int duration);

    /**
     * Hides the keyboard on a mobile device.
     */
    void mobileHideKeyboard();

    /**
     * Sets the mobile device's orientation to landscape.
     */
    void mobileSetLandscape();

    /**
     * Sets the mobile device's orientation to portrait.
     */
    void mobileSetPortrait();

    /**
     * Sets the mobile device's GPS location.
     */
    void mobileSetGeoLocation(double latitude, double longitude, double altitude);
}
