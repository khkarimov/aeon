package aeon.core.framework.abstraction.drivers;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBrowserType;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The framework adapter interface.
 */
public interface IWebDriver extends IDriver {

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     */
    void close();

    /**
     * Finds the first Element using the given mechanism.
     *
     * @param selector The locating mechanism to use.
     * @return The first matching Element on the current product.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebControl findElement(IByWeb selector);

    /**
     * Finds all Elements within the current product
     * using the given mechanism.
     *
     * @param selector The locating mechanism to use.
     * @return A ReadOnly Collection of found elements.
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementsException  If there is no such element.
     */
    Collection<WebControl> findElements(IByWeb selector);

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
     * Accepts the currently active modal dialog for this particular driver instance.
     */
    void acceptAlert();

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     */
    void dismissAlert();

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @return The value of the text in the alert.
     */
    String getAlertText();

    /**
     * Scrolls to the element on the page if in Chrome.
     *
     * @param element The selector.
     */
    void scrollElementIntoView(WebControl element);

    /**
     * Scrolls to the top of the window.
     */
    void scrollToTop();

    /**
     * Scrolls to the end of the window.
     */
    void scrollToEnd();

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     */
    void switchToDefaultContent();

    /**
     * Focuses the currently selected window.
     */
    void focusWindow();

    /**
     * Selects the first frame using the given method.
     *
     * @param selector The locating mechanism to use.
     * @throws IllegalArgumentException    If FindBy is null.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void switchToFrame(IByWeb selector);

    /**
     * Gets the HTML Tag type of the given element.
     *
     * @param element The element to get the Tag of.
     * @return The type of Tag.
     */
    String getElementTagName(WebControl element);

    /**
     * Executes JavaScript in the product of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object executeScript(String script);

    /**
     * Executes asynchronous JavaScript in the product of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object executeAsyncScript(String script);

    /**
     * Clears the text of the given Element.
     *
     * @param element The element to be cleared.
     */
    void clearElement(WebControl element);

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    void addCookie(IWebCookie cookie);

    /**
     * Deletes a cookie.
     *
     * @param cookie The name of the cookie to be modified.
     */
    void deleteCookie(String cookie);

    /**
     * Deletes all cookies.
     */
    void deleteAllCookies();

    /**
     * Move back a single entry in the browser's history.
     */
    void goBack();

    /**
     * Move a single "item" forward in the browser's history.
     * <p>
     * Does nothing if we are on the latest page viewed.
     */
    void goForward();

    /**
     * Load a new web page in the current browser window.
     *
     * @param url The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If URL is null.
     */
    String goToUrl(URL url);

    /**
     * Maximizes the current window if it is not already maximized.
     */
    void maximize();

    /**
     * Refreshes the current page.
     */
    void refresh();

    /**
     * Choose a Dropdown Element by its Value.
     *
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void chooseSelectElementByValue(WebControl element, String value);

    /**
     * Choose a Dropdown Element by its Text.
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
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param title The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If windowTitle is null.
     * @throws IllegalArgumentException If windowTitle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String switchToWindowByTitle(String title);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param url The url of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If url is null.
     * @throws IllegalArgumentException If url is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String switchToWindowByUrl(String url);

    /**
     * Resizes the current window.
     *
     * @param size The new browser size.
     */
    void resize(Dimension size);

    /**
     * Blurs the current element, takes off focus.
     * A globally unique identifier associated with this call
     *
     * @param element The element to be blurred
     */
    void blur(WebControl element);

    /**
     * Right clicks an element.
     *
     * @param element The element to right click.
     */
    void rightClick(WebControl element);

    /**
     * Checks a checkbox.
     *
     * @param element The checkbox to be checked.
     */
    void check(WebControl element);

    /**
     * Unchecks a checkbox.
     *
     * @param element The checkbox to be unchecked.
     */
    void unCheck(WebControl element);

    /**
     * Holds a click on an element for the duration specified.
     *
     * @param element  The web element to click.
     * @param duration click for at least this long (in milliseconds).
     */
    void clickAndHold(WebControl element, int duration);

    /**
     * Checks that an element is enabled.
     *
     * @param element The web element to check.
     */
    void isElementEnabled(WebControl element);

    /**
     * If this method was called then the element exists. Logic done at command initialization
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
     * Asserts that a select element possesses all of the elements passed to it. It can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched by either their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the element should possess.
     * @param optgroup The optional option group that which will be searched.
     * @param select   The method by which the options will be searched either by value or by text.
     */
    void hasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element does not possess any of the options passed. Can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched for either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the select element should not have.
     * @param optgroup The optional option group that will be searched.
     * @param select   The method by which the options will be searched for either by visible text or their value.
     */
    void doesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

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
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param keysToSend The keystrokes to send.
     */
    void sendKeysToAlert(String keysToSend);

    /**
     * Drags one element and drops it into another.
     *
     * @param dropElement   The element to drop.
     * @param targetElement The target element.
     */
    void dragAndDrop(WebControl dropElement, IByWeb targetElement);

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
     * Sets the Value of a Body element by Javascript.
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void setBodyValueByJavaScript(WebControl element, String value);

    /**
     * Sets the Value of an Element by Javascript.
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
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
    void clickAllElements(IByWeb elementsBy);

    /**
     * Asserts that a select element not only has all of the options provided by that they are all in the order provided. Can optionally be passed an option group
     * that if non-null will be searched in isolation instead of the entire select. Options can be searched either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options to be searched for.
     * @param optgroup The visible text of the optional option group which would be searched.
     * @param select   The method by which the options will be searched, either by text or value.
     */
    void hasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select);

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
     * Asserts the Title of a Page is equal to a given String.
     *
     * @param comparingText String to compare against Page Title.
     */
    void verifyTitle(String comparingText);

    /**
     * Asserts the URL is equal to the given URL
     * A globally unique identifier associated with this call.
     *
     * @param comparingURL The URL to compare against
     */
    void verifyURL(URL comparingURL);

    /**
     * Asserts that an element is selected.
     *
     * @param element The element to check
     */
    void notSelected(WebControl element);

    /**
     * Asserts that an element is not visible.
     *
     * @param element The element to check
     */
    void notVisible(WebControl element);

    /**
     * Asserts that an element is not selected.
     *
     * @param element The element to check
     */
    void selected(WebControl element);

    /**
     * Asserts that an element is visible.
     *
     * @param element The element to check
     */
    void visible(WebControl element);


    /**
     * Gets the list of all cookies.
     *
     * @return The list of cookies.
     */
    Collection<IWebCookie> getAllCookies();

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    void modifyCookie(String name, String value);

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    IWebCookie getCookie(String name);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the children's visible text will be searched or an attribute.
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
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void hasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not possess a text.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the children should not possess.
     * @param selector  The selector for the children to be searched.
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void doesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not possess a text. Comparisons made ignoring case and whitespace.
     *
     * @param element   The web element to be searched.
     * @param messages  The text that the children should not possess.
     * @param selector  The selector for the children to be searched.
     * @param option    Whether the children's visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void doesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the children's visible text will be searched or an attribute.
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
     * Checks that an element is disabled.
     *
     * @param element The web element to check.
     */
    void isElementDisabled(WebControl element);

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. has a
     * Margin of error. The date must be in the ISO-8601 standard.
     *
     * @param element       The element that possesses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expected      The expected date that the attribute should possess.
     * @param delta         The margin of error that the date can be within. Cannot possess any weeks, months or years due to
     *                      them having variable lengths.
     */
    void datesApproximatelyEqual(WebControl element, String attributeName, LocalDate expected, Period delta);

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return Returns the BrowserType associated with this browser.
     */
    IBrowserType getBrowserType();

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
     * Gets the bounding rectangle for an element.
     *
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    ClientRects getClientRects(WebControl element);

    /**
     * Sends a non-alphanumeric keys to an element.
     *
     * @param element The element to receive the keys.
     * @param key     The key to be sent.
     */
    void pressKeyboardKey(WebControl element, KeyboardKey key);

    /**
     * Sets the Text or Value of an element. A globally unique identifier associated with the call.
     *
     * @param element  The web element that is being modified.
     * @param option   Enum which determined whether to set tht Text or the Value.
     * @param setValue The new value for the Text or Value attribute of the control.
     */
    void set(WebControl element, WebSelectOption option, String setValue);

    /**
     * Asserts a window with a given title does not exists. A globally unique identifier associated with the call.
     *
     * @param windowTitle The title of the window to search for.
     * @return The title of the window
     */
    String windowDoesNotExistByTitle(String windowTitle);

    /**
     * Asserts a window with a given URL does not exists. A globally unique identifier associated with the call.
     *
     * @param url The URL of the window to search for.
     * @return The URL of the window
     */
    String windowDoesNotExistByUrl(String url);

    /**
     * Selects a file for inputs of type file.
     *
     * @param control The control which gets the file path.
     * @param path    The path of the file to select.
     */
    void selectFile(WebControl control, String path);
}
