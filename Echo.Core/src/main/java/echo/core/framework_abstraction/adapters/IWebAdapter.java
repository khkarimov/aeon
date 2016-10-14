package echo.core.framework_abstraction.adapters;

import com.sun.glass.ui.Size;
import echo.core.common.CompareType;
import echo.core.common.ComparisonOption;
import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.*;
import echo.core.common.web.BrowserType;
import echo.core.common.web.ClientRects;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.controls.web.WebControl;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by DionnyS on 4/20/2016.
 */
public interface IWebAdapter extends IAdapter {

    /**
     * Gets the title of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The title of the current browser window.
     */
    String GetTitle(UUID guid);

    /**
     * Gets the URL the browser is currently displaying.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The URL the browser is currently displaying.
     */
    URL GetUrl(UUID guid);

    /**
     * Gets the current window handle, which is an opaque handle to this window that uniquely identifies it within this driver instance.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The current window handle, which is an opaque handle to this window that uniquely identifies it within this driver instance.
     */
    String GetCurrentWindowHandle(UUID guid);

    /**
     * Gets the window handles of open browser windows.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The window handles of open browser windows.
     */
    Collection<String> GetWindowHandles(UUID guid);

    /**
     * Load a new web page in the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param url  The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If URL is null.
     */
    String GoToUrl(UUID guid, URL url);

    /**
     * Scrolls to the top of the window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void ScrollToTop(UUID guid);

    /**
     * Scrolls to the end of the window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void ScrollToEnd(UUID guid);

    /**
     * Finds the first Element using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching Element on the current product.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebControl FindElement(UUID guid, IBy findBy);

    /**
     * Finds all Elements within the current product
     * using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return A ReadOnly Collection of found elements.
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<WebControl> FindElements(UUID guid, IBy findBy);

    /**
     * Finds the first Select Element using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching Select Element on the current product.
     * @throws IllegalArgumentException                 If FindBy is null.
     * @throws NoSuchElementException                   If there is no such element.
     * @throws ScriptReturnValueNotHtmlElementException If the JavaScript did not return an HTML element.
     * @throws UnsupportedSelectElementException        If the select element is not supported.
     */
    WebControl FindSelectElement(UUID guid, IBy findBy);

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void SwitchToDefaultContent(UUID guid);

    /**
     * Selects the first frame using the given method.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @throws IllegalArgumentException    If FindBy is null.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void SwitchToFrame(UUID guid, IBy findBy);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param guid        A globally unique identifier associated with this call.
     * @param windowTitle The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If windowTitle is null.
     * @throws IllegalArgumentException If windowTitle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByTitle(UUID guid, String windowTitle);

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param handle The handle of the window to select.
     * @throws IllegalArgumentException If handle is null.
     * @throws IllegalArgumentException If handle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void SwitchToWindowByHandle(UUID guid, String handle);

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The URL to which to switch focus.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If value is null.
     * @throws IllegalArgumentException If value is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByUrl(UUID guid, String value);

    /**
     * Close the current window, quitting the browser if it is the last window currently open.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Close(UUID guid);

    /**
     * Quits this driver, closing every associated window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Quit(UUID guid);

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @param guid A globally unique identifier associated with this call.
     * @throws NoAlertException If the alert does not exist.
     */
    void VerifyAlertExists(UUID guid);

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @param guid A globally unique identifier associated with this call.
     * @throws AlertExistsException If the alert exists.
     */
    void VerifyAlertNotExists(UUID guid);

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The value of the text in the alert.
     */
    String GetAlertText(UUID guid);

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param guid       A globally unique identifier associated with this call.
     * @param keysToSend The keystrokes to send.
     */
    void SendKeysToAlert(UUID guid, String keysToSend);

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void AcceptAlert(UUID guid);

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void DismissAlert(UUID guid);

    /**
     * Focuses the currently selected window.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void FocusWindow(UUID guid);

    /**
     * Executes JavaScript in the product of the currently selected frame or window.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object ExecuteScript(UUID guid, String script, Object... args);

    /**
     * Move back a single entry in the browser's history.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Back(UUID guid);

    /**
     * Move a single "item" forward in the browser's history.
     *
     * @param guid A globally unique identifier associated with this call.
     *             Does nothing if we are on the latest page viewed.
     */
    void Forward(UUID guid);

    /**
     * Refreshes the current page.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Refresh(UUID guid);

    /**
     * Maximizes the current window if it is not already maximized.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void Maximize(UUID guid);

    /**
     * Resizes the current window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param size The new browser size.
     */
    void Resize(UUID guid, Size size);

    /**
     * Opens on a type input type=file.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The selector for the element.
     */
    void OpenFileDialog(UUID guid, IBy selector);

    /**
     * Types keys of file indicated by provided path
     * REQUIRES DIALOG BOX TO ALREADY BE OPENED BY OpenFileDialog
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void SelectFileDialog(UUID guid, IBy selector, String path);

    /**
     * Opens a windows select file dialog and selects
     * file indicated by provided path
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void UploadFileDialog(UUID guid, IBy selector, String path);

    /**
     * Scrolls to the element on the page if in Chrome.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The selector.
     */
    void ScrollElementIntoView(UUID guid, WebControl element);

    /**
     * Gets a screenshot of the currently focus browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return Image of the browser window.
     */
    Image GetScreenshot(UUID guid);

    /**
     * Gets the source of the current browser window.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The current page's source as a string.
     */
    String GetPageSource(UUID guid);

    /**
     * Drags one element and drops it into another.
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param dropElement   The element to drop.
     * @param targetElement The target element.
     */
    void DragAndDrop(UUID guid, IBy dropElement, IBy targetElement);

    /**
     * Right clicks an element.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element The element to right click.
     */
    void RightClick(UUID guid, WebControl element);

    /**
     * Clicks an element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to click.
     */
    void Click(UUID guid, WebControl element);

    /**
     * Double clicks an element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to double click.
     */
    void DoubleClick(UUID guid, WebControl element);

    /**
     * Holds a click on an element for the duration specified.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The web element to click.
     * @param duration Click for at least this long (in milliseconds).
     */
    void ClickAndHold(UUID guid, WebControl element, int duration);

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to click.
     * @param x       The x offset.
     * @param y       The y offset.
     */
    void ClickAtOffset(UUID guid, WebControl element, int x, int y);

    /**
     * Refreshes a Frame.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void RefreshFrame(UUID guid);

    /**
     * Gets the list of all cookies.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The list of cookies.
     */
    java.util.List<IWebCookie> GetAllCookies(UUID guid);

    /**
     * Gets a cookie.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    IWebCookie GetCookie(UUID guid, String name);

    /**
     * Adds a cookie.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param cookie The cookie to be added.
     */
    void AddCookie(UUID guid, IWebCookie cookie);

    /**
     * Deletes a cookie.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param name The name of the cookie to be modified.
     */
    void DeleteCookie(UUID guid, String name);

    /**
     * Deletes all cookies.
     *
     * @param guid A globally unique identifier associated with this call.
     */
    void DeleteAllCookies(UUID guid);

    /**
     * Modifies the value of a cookie.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    void ModifyCookie(UUID guid, String name, String value);

    /**
     * Gets the currently focused element.
     *
     * @param guid A globally unique identifier associated with this call.
     * @return The currently focused element.
     */
    WebControl GetFocusedElement(UUID guid);

    /**
     * Gets the HTML Tag type of the given element
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to get the Tag of.
     * @return The type of Tag.
     */
    String GetElementTagName(UUID guid, WebControl element);

    /**
     * Clears the text of the given Element
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to be cleared.
     */
    void ClearElement(UUID guid, WebControl element);

    /**
     * Choose a Select Element by its Value.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void ChooseSelectElementByValue(UUID guid, WebControl element, String value);

    /**
     * Choose a Select Element by its Text.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void ChooseSelectElementByText(UUID guid, WebControl element, String value);

    /**
     * Clicks on the given element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to be clicked.
     */
    void ClickElement(UUID guid, WebControl element);

    /**
     * Sends Keys to the given element programmatically
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to receive the keys.
     * @param value   The keys to be sent to the element.
     */
    void SendKeysToElement(UUID guid, WebControl element, String value);

    /**
     * Gets the value of a given attribute of a given element
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to get the attribute values of.
     * @param value   The name of the attribute to get the value of.
     * @return The value of the element attribute
     */
    String GetElementAttribute(UUID guid, WebControl element, String value);

    /**
     * Switches to the Main Window
     *
     * @param guid
     * @param mainWindowHandle
     * @param waitForAllPopupWindowsToClose
     */
    void SwitchToMainWindow(UUID guid, String mainWindowHandle, Boolean waitForAllPopupWindowsToClose);

    /**
     * Blurs the current element, takes off focus.
     *
     * @param guid    A globally unique identifier associated with this call
     * @param element The element to be blurred
     */
    void Blur(UUID guid, WebControl element);

    /**
     * Checks a checkbox.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The checkbox to be checked.
     */
    void CheckElement(UUID guid, WebControl element);

    /**
     * Unchecks a checkbox
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The checkbox to be unchecked.
     */
    void UnCheckElement(UUID guid, WebControl element);

    /**
     * Checks that an element is enabled
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to check.
     */
    void IsElementEnabled(UUID guid, WebControl element);

    /**
     * Checks that an element is disabled.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to check.
     */
    void IsElementDisabled(UUID guid, WebControl element);

    /**
     * Checks to see if a selected element's checkbox is selected
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The select element.
     */
    void Selected(UUID guid, WebControl element);

    /**
     * Checks to see if a selected element's checkbox is not selected
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The select element.
     */
    void NotSelected(UUID guid, WebControl element);

    /**
     * Checks to see if a selected element is visible
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The select element.
     */
    void Visible(UUID guid, WebControl element);

    /**
     * Checks to see if a selected element is hidden
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The select element.
     */
    void NotVisible(UUID guid, WebControl element);

    /**
     * If this method was called then the element exists. Logic done at command initialization
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element.
     */
    void Exists(UUID guid, WebControl element);

    /**
     * If this method was reached then the element exists when it should not.
     * Logic done at command initialization.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element.
     */
    void NotExists(UUID guid, WebControl element);

    /**
     * Asserts that a select element posseses all of the elements passed to it. It can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched by either their value or their visible text.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The select element.
     * @param options  The options that the element should posses.
     * @param optgroup The optional option group that which will be searched.
     * @param select   The method by which the options will be searched either by value or by text.
     */
    void ElementHasOptions(UUID guid, WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element does not posses any of the options passed. Can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched for either by their value or their visible text.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The select element.
     * @param options  The options that the select element should not have.
     * @param optgroup The optional option group that will be searched.
     * @param select   The method by which the options will be searched for either by visible text or their value.
     */
    void ElementDoesNotHaveOptions(UUID guid, WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Moves the mouse cursor off of an element
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to Mouse Out of
     */
    void MouseOut(UUID guid, WebControl element);

    /**
     * Moves the mouse cursor on top of an element
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to Mouse Over
     */
    void MouseOver(UUID guid, WebControl element);

    /**
     * Asserts that the selected element's body tag will be changed into the provided String value
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element By The selector.
     * @param value   Html to be inserted into body tag
     */
    void SetBodyValueByJavaScript(UUID guid, WebControl element, String value);

    /**
     * Asserts that the selected element's value tag will be changed into the provided String value
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element By The selector.
     * @param value   Html to be inserted into a value tag
     */
    void SetTextByJavaScript(UUID guid, WebControl element, String value);

    /**
     * Sets the Value of a Div element by Javascript
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void SetDivValueByJavaScript(UUID guid, WebControl element, String value);

    /**
     * Clicks all elements that corresponding with the given IBy.
     *
     * @param guid       A globally unique identifier associated with this call.
     * @param elementsBy The selector.
     */
    void ClickAllElements(UUID guid, IBy elementsBy);

    /**
     * Asserts that a select element not only has all of the options provided by that they are all in the order provided. Can optionally be passed an option group
     * that if non-null will be searched in isolation instead of the entire select. Options can be searched either by their value or their visible text.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The select element.
     * @param options  The options to be searched for.
     * @param optgroup The visible text of the optional option group which would be searched.
     * @param select   The method by which the options will be searched, either by text or value.
     */
    void ElementHasOptionsInOrder(UUID guid, WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element has a certain amount of options. Can optionally be passed an option group that if non-null will be searched
     * in isolation instead of the he entire select.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The select element.
     * @param optnumber The amount of elements the select should have.
     * @param optgroup  The visible text of the optional option group which would be searched.
     */
    void HasNumberOfOptions(UUID guid, WebControl element, int optnumber, String optgroup);

    /**
     * Asserts that a select element has all of its options in a certain order, either ascending or descending alphanumerically by either their value
     * or their visible text.
     *
     * @param guid     A gobally unique identifier associated with this call.
     * @param element  The select element.
     * @param compare  The method by which the options will be compared.
     * @param optGroup The optional option group which would be searched in isolation instead.
     */
    void HasAllOptionsInOrder(UUID guid, WebControl element, CompareType compare, String optGroup);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void Has(UUID guid, WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     * Comparisons are made ignoring whitespace and case.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void HasLike(UUID guid, WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    void DoesNotHave(UUID guid, WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text. Comparisons made ignoring case and whitespace.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    void DoesNotHaveLike(UUID guid, WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void HasOnly(UUID guid, WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void Is(UUID guid, WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void IsLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param guid      A globally unique identifier associated with this call.
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void IsNotLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that the Text of given Alert is same as given string
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param comparingText String to compare against Alert Text.
     */
    void VerifyAlertText(UUID guid, String comparingText);

    /**
     * Asserts that the Text of an Alert is Like a given string(spacing and cases* ignored).
     * caseSensitive is an option
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param comparingText String to compare against Alert Text.
     * @param caseSensitive Determine caseSensitive comparison
     */
    void VerifyAlertTextLike(UUID guid, String comparingText, boolean caseSensitive);

    /**
     * Asserts the Title of a Page is equal to a given String
     *
     * @param guid           A globally unique identifier associated with this call.
     * @param comparingTitle String to compare against Page Title.
     */
    void VerifyTitle(UUID guid, String comparingTitle);

    /**
     * Asserts the URL is equal to the given URL
     *
     * @param guid         A globally unique identifier associated with this call.
     * @param comparingURL The URL to compare against
     */
    void VerifyURL(UUID guid, URL comparingURL);

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. Has a
     * Margin of error. The date must be in the ISO-8601 standard.
     *
     * @param guid          A globally unique identifier associated with this call.
     * @param element       The element that posseses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expected      The expected date that the attribute should posses.
     * @param delta         The margin of error that the date can be within. Cannot posses any weeks, months or years due to
     *                      them having variable lengths.
     */
    void DatesApproximatelyEqual(UUID guid, WebControl element, String attributeName, DateTime expected, Period delta);

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @param guid A Globally unique identifier associated with this call.
     * @return Returns the BrowserType associated with this browser.
     */
    BrowserType GetBrowserType(UUID guid);

    /**
     * Gets the bounding rectangle for an element.
     *
     * @param guid    A Globally unique identifier associated with this call.
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    ClientRects GetClientRects(UUID guid, WebControl element);

    /**
     * Sends a non-alphanumeric keys to an element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The element to recieve the keys.
     * @param key     The key to be sent.
     */
    void PressKeyboardKey(UUID guid, WebControl element, KeyboardKey key);

    /**
     * Asserts a window with a given title does not exists.
     * @param guid A globally unique identifier associated with the call.
     * @param windowTitile The title of the window to search for.
     * @return The title of the window
     */
    String WindowDoesNotExistByTitle(UUID guid, String windowTitile);

    /**
     * Asserts a window with a given URL does not exists.
     * @param guid A globally unique identifier associated with the call.
     * @param url The URL of the window to search for.
     * @return The URL of the window
     */
    String WindowDoesNotExistByUrl(UUID guid, String url);

    /**
     * Sets the Text or Value of an element.
     * @param guid A globally unique identifier associated with the call.
     * @param element The web element that is being modified.
     * @param option Enum which determined whether to set tht Text or the Value.
     * @param setValue The new value for the Text or Value attribute of the control.
     */
    void Set(UUID guid, WebControl element, WebSelectOption option, String setValue);

}
