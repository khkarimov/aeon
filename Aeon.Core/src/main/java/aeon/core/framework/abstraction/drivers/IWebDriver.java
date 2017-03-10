package aeon.core.framework.abstraction.drivers;

import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import com.sun.glass.ui.Size;
import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.net.URL;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * The framework adapter interface.
 */
public interface IWebDriver extends IDriver {

    /**
     * Finds the first Element using the given mechanism.
     *
     * @param selector The locating mechanism to use.
     * @return The first matching Element on the current product.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebControl FindElement(IBy selector);

    /**
     * Finds all Elements within the current product
     * using the given mechanism.
     *
     * @param selector The locating mechanism to use.
     * @return A ReadOnly Collection of found elements.
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If FindBy is null.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<WebControl> FindElements(IBy selector);

    /**
     * Clicks an element.
     *
     * @param element The web element to click.
     */
    void Click(WebControl element);

    /**
     * Double clicks an element.
     *
     * @param element The web element to double click.
     */
    void DoubleClick(WebControl element);

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     *
     */
    void AcceptAlert();

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     *
     */
    void DismissAlert();

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @return The value of the text in the alert.
     */
    String GetAlertText();

    /**
     * Scrolls to the element on the page if in Chrome.
     *
     * @param element The selector.
     */
    void ScrollElementIntoView(WebControl element);

    /**
     * Scrolls to the top of the window.
     *
     */
    void ScrollToTop();

    /**
     * Scrolls to the end of the window.
     *
     */
    void ScrollToEnd();

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     *
     */
    void SwitchToDefaultContent();

    /**
     * Focuses the currently selected window.
     *
     */
    void FocusWindow();

    /**
     * Selects the first frame using the given method.
     *
     * @param selector The locating mechanism to use.
     * @throws IllegalArgumentException    If FindBy is null.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void SwitchToFrame(IBy selector);

    /**
     * Gets the HTML Tag type of the given element
     *
     * @param element The element to get the Tag of.
     * @return The type of Tag.
     */
    String GetElementTagName(WebControl element);

    /**
     * Executes JavaScript in the product of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object ExecuteScript(String script);

    /**
     * Clears the text of the given Element
     *
     * @param element The element to be cleared.
     */
    void ClearElement(WebControl element);

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    void AddCookie(IWebCookie cookie);

    /**
     * Deletes a cookie.
     *
     * @param cookie The name of the cookie to be modified.
     */
    void DeleteCookie(String cookie);

    /**
     * Deletes all cookies.
     *
     */
    void DeleteAllCookies();

    /**
     * Move back a single entry in the browser's history.
     *
     */
    void GoBack();

    /**
     * Move a single "item" forward in the browser's history.
     *
     *             Does nothing if we are on the latest page viewed.
     */
    void GoForward();

    /**
     * Load a new web page in the current browser window.
     *
     * @param url  The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If URL is null.
     */
    String GoToUrl(URL url);

    /**
     * Maximizes the current window if it is not already maximized.
     *
     */
    void Maximize();

    /**
     * Refreshes the current page.
     *
     */
    void Refresh();

    /**
     * Choose a Select Element by its Value.
     *
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void ChooseSelectElementByValue(WebControl element, String value);

    /**
     * Choose a Select Element by its Text.
     *
     * @param element The element to be chosen by value.
     * @param value   The value to search for.
     */
    void ChooseSelectElementByText(WebControl element, String value);

    /**
     * Clicks on the given element.
     *
     * @param element The element to be clicked.
     */
    void ClickElement(WebControl element);

    /**
     * Sends Keys to the given element programmatically
     *
     * @param element The element to receive the keys.
     * @param value   The keys to be sent to the element.
     */
    void SendKeysToElement(WebControl element, String value);

    /**
     * Gets the value of a given attribute of a given element
     *
     * @param element The element to get the attribute values of.
     * @param value   The name of the attribute to get the value of.
     * @return The value of the element attribute
     */
    String GetElementAttribute(WebControl element, String value);

    /**
     * Switches to the Main Window
     *
     * @param mainWindowHandle The handle of the main window
     * @param waitForAllPopupWindowsToClose
     */
    void SwitchToMainWindow(String mainWindowHandle, Boolean waitForAllPopupWindowsToClose);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param title The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If windowTitle is null.
     * @throws IllegalArgumentException If windowTitle is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByTitle(String title);

    String SwitchToWindowByUrl(String url);

    /**
     * Resizes the current window.
     *
     * @param size The new browser size.
     */
    void Resize(Size size);

    /**
     * Blurs the current element, takes off focus.
     *    A globally unique identifier associated with this call
     * @param element The element to be blurred
     */
    void Blur(WebControl element);

    /**
     * Right clicks an element.
     *
     * @param element The element to right click.
     */
    void RightClick(WebControl element);

    /**
     * Checks a checkbox.
     *
     * @param element The checkbox to be checked.
     */
    void Check(WebControl element);

    /**
     * Unchecks a checkbox
     *
     * @param element The checkbox to be unchecked.
     */
    void UnCheck(WebControl element);

    /**
     * Holds a click on an element for the duration specified.
     *
     * @param element  The web element to click.
     * @param duration Click for at least this long (in milliseconds).
     */
    void ClickAndHold(WebControl element, int duration);

    /**
     * Checks that an element is enabled
     *
     * @param element The web element to check.
     */
    void IsElementEnabled(WebControl element);

    /**
     * If this method was called then the element exists. Logic done at command initialization
     *
     * @param element The web element.
     */
    void Exists(WebControl element);

    /**
     * If this method was reached then the element exists when it should not.
     * Logic done at command initialization.
     *
     * @param element The web element.
     */
    void NotExists(WebControl element);

    /**
     * Asserts that a select element posseses all of the elements passed to it. It can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched by either their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the element should posses.
     * @param optgroup The optional option group that which will be searched.
     * @param select   The method by which the options will be searched either by value or by text.
     */
    void HasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element does not posses any of the options passed. Can optionally be passed an option group that if non-null will be searched instead of the entire
     * select tag. Options will be searched for either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options that the select element should not have.
     * @param optgroup The optional option group that will be searched.
     * @param select   The method by which the options will be searched for either by visible text or their value.
     */
    void DoesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Opens on a type input type=file.
     *
     * @param selector The selector for the element.
     */
    void OpenFileDialog(IBy selector);

    /**
     * Types keys of file indicated by provided path
     * REQUIRES DIALOG BOX TO ALREADY BE OPENED BY OpenFileDialog
     *
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void SelectFileDialog(IBy selector, String path);

    /**
     * Opens a windows select file dialog and selects
     * file indicated by provided path
     *
     * @param selector The selector for the element.
     * @param path     The path to the file to be selected.
     */
    void UploadFileDialog(IBy selector, String path);

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @throws NoAlertException If the alert does not exist.
     */
    void VerifyAlertExists();

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @throws AlertExistsException If the alert exists.
     */
    void VerifyAlertNotExists();

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param keysToSend The keystrokes to send.
     */
    void SendKeysToAlert(String keysToSend);

    /**
     * Drags one element and drops it into another.
     *
     * @param dropElement   The element to drop.
     * @param targetElement The target element.
     */
    void DragAndDrop(WebControl dropElement, IBy targetElement);

    /**
     * Moves the mouse cursor off of an element
     *
     * @param element The element to Mouse Out of
     */
    void MouseOut(WebControl element);

    /**
     * Moves the mouse cursor on top of an element
     *
     * @param element The element to Mouse Over
     */
    void MouseOver(WebControl element);

    /**
     * Sets the Value of a Body element by Javascript
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void SetBodyValueByJavaScript(WebControl element, String value);

    /**
     * Sets the Value of an Element by Javascript
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void SetTextByJavaScript(WebControl element, String value);

    /**
     * Sets the Value of a Div element by Javascript
     *
     * @param element The element whose value is set
     * @param value   The value to set to the element
     */
    void SetDivValueByJavaScript(WebControl element, String value);

    /**
     * Clicks all elements that corresponding with the given IBy.
     *
     * @param elementsBy The selector.
     */
    void ClickAllElements(IBy elementsBy);

    /**
     * Asserts that a select element not only has all of the options provided by that they are all in the order provided. Can optionally be passed an option group
     * that if non-null will be searched in isolation instead of the entire select. Options can be searched either by their value or their visible text.
     *
     * @param element  The select element.
     * @param options  The options to be searched for.
     * @param optgroup The visible text of the optional option group which would be searched.
     * @param select   The method by which the options will be searched, either by text or value.
     */
    void HasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select);

    /**
     * Asserts that a select element has a certain amount of options. Can optionally be passed an option group that if non-null will be searched
     * in isolation instead of the he entire select.
     *
     * @param element   The select element.
     * @param optnumber The amount of elements the select should have.
     * @param optgroup  The visible text of the optional option group which would be searched.
     */
    void HasNumberOfOptions(WebControl element, int optnumber, String optgroup);

    /**
     * Asserts that a select element has all of its options in a certain order, either ascending or descending alphanumerically by either their value
     * or their visible text.
     *
     * @param element  The select element.
     * @param compare  The method by which the options will be compared.
     * @param optGroup The optional option group which would be searched in isolation instead.
     */
    void HasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup);

    /**
     * Asserts that the Text of given Alert is same as given string
     *
     * @param comparingText String to compare against Alert Text.
     */
    void VerifyAlertText(String comparingText);

    /**
     * Asserts that the Text of an Alert is Like a given string(spacing and cases* ignored).
     * caseSensitive is an option
     *
     * @param comparingText String to compare against Alert Text.
     * @param caseSensitive Determine caseSensitive comparison
     */
    void VerifyAlertTextLike(String comparingText, boolean caseSensitive);

    /**
     * Asserts the Title of a Page is equal to a given String
     *
     * @param comparingText String to compare against Page Title.
     */
    void VerifyTitle(String comparingText);

    /**
     * Asserts the URL is equal to the given URL
     *         A globally unique identifier associated with this call.
     * @param comparingURL The URL to compare against
     */
    void VerifyURL(URL comparingURL);

    void NotSelected(WebControl element);

    void NotVisible(WebControl element);

    void Selected(WebControl element);

    void Visible(WebControl element);
//

    /**
     * Gets the list of all cookies.
     *
     * @return The list of cookies.
     */
    Collection<IWebCookie> GetAllCookies();

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    void ModifyCookie(String name, String value);

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    IWebCookie GetCookie(String name);

    /**
     * Asserts that an elements children that match a given selector contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void Has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

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
    void HasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text.
     *
     * @param element  The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    void DoesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children do not posses a text. Comparisons made ignoring case and whitespace.
     *
     * @param element  The web element to be searched.
     * @param messages The text that the chilren should not posses.
     * @param selector The selector for the children to be searched.
     */
    void DoesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an elements children that match a given selector only contain either the visible text or the named attribute.
     *
     * @param element   The web control whose children are to be searched.
     * @param messages  The strings to be compared to.
     * @param selector  The selectors that the children will be matched to.
     * @param option    Whether the childrens visible text will be searched or an attribute.
     * @param attribute The attribute that will be searched.
     */
    void HasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void Is(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Asserts that an element's attribute is equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void IsLike(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Checks that an element is disabled.
     *
     * @param element The web element to check.
     */
    void IsElementDisabled(WebControl element);

    /**
     * Obtains a date from an elements attribute and compares it with an expected date. Has a
     * Margin of error. The date must be in the ISO-8601 standard.
     *
     * @param element       The element that posseses the date.
     * @param attributeName The name of the attribute that has the date.
     * @param expected      The expected date that the attribute should posses.
     * @param delta         The margin of error that the date can be within. Cannot posses any weeks, months or years due to
     *                      them having variable lengths.
     */
    void DatesApproximatelyEqual(WebControl element, String attributeName, DateTime expected, Period delta);

    /**
     * Returns the enumerable BrowserType representing the current browser.
     *
     * @return Returns the BrowserType associated with this browser.
     */
    BrowserType GetBrowserType();

    /**
     * Asserts that an element's attribute is not equal to a given value. Comparison made ignoring whitespace and case.
     *
     * @param element   The web element.
     * @param value     The value the attribute should be.
     * @param option    Whether the innerhtml will be evaluated by the literal html code or the visible text.
     * @param attribute The attribute.
     */
    void IsNotLike(WebControl element, String value, ComparisonOption option, String attribute);

    /**
     * Gets the bounding rectangle for an element.
     *
     * @param element The element whose rects are to be returned.
     * @return Returns a ClientRects object with the four sides of the bounding rectangle.
     */
    ClientRects GetClientRects(WebControl element);

    /**
     * Sends a non-alphanumeric keys to an element.
     *
     * @param element The element to recieve the keys.
     * @param key     The key to be sent.
     */
    void PressKeyboardKey(WebControl element, KeyboardKey key);

    /**
     * Sets the Text or Value of an element. A globally unique identifier associated with the call.
     * @param element The web element that is being modified.
     * @param option Enum which determined whether to set tht Text or the Value.
     * @param setValue The new value for the Text or Value attribute of the control.
     */
    void Set(WebControl element, WebSelectOption option, String setValue);

    /**
     * Asserts a window with a given title does not exists. A globally unique identifier associated with the call.
     * @param windowTitle The title of the window to search for.
     * @return The title of the window
     */
    String WindowDoesNotExistByTitle(String windowTitle);

    /**
     * Asserts a window with a given URL does not exists. A globally unique identifier associated with the call.
     * @param url The URL of the window to search for.
     * @return The URL of the window
     */
    String WindowDoesNotExistByUrl(String url);
//    /**
//
//   * Check the element.
//     *
//     * @param parameterObject The parameter object.
//     * @return Wether or not the element was checked.
//     */
//    boolean Check(ParameterObject parameterObject);
//
//    /**
//     * Uncheck the element.
//     *
//     * @param parameterObject The parameter object.
//     * @return Wether or not the element was unchecked.
//     */
//    boolean Uncheck(ParameterObject parameterObject);
//
//    /**
//     * Double clicks the element.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void DoubleClick(ParameterObject parameterObject);
//
//    /**
//     * Right clicks the element.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void RightClick(ParameterObject parameterObject);
//
//    /**
//     * Clicks and holds on the element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void ClickAndHold(ParameterObject parameterObject);
//
//    /**
//     * Control click the element.
//     *
//     * @param parameterObject The paramater object.
//     */
//    void ControlClick(ParameterObject parameterObject);
//
//    /**
//     * Set a value.
//     *
//     * @param parameterObject The Parameter Object.
//     */
//    void Set(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is empty.
//     *
//     * @param parameterObject The element being set.
//     */
//    void IsEmpty(ParameterObject parameterObject);
//
//    /**
//     * Clears the element.
//     *
//     * @param parameterObject The Parameter Object.
//     */
//    void Clear(ParameterObject parameterObject);
//
//    /**
//     * Mouses over the element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void MouseOver(ParameterObject parameterObject);
//
//    /**
//     * Mouses out of the element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void MouseOutOf(ParameterObject parameterObject);
//
//    /**
//     * Drags the element to the destination.
//     *
//     * @param parameterObject The Parameter Object.
//     */
//    void DragAndDrop(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is visible.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void IsVisible(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element exists.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Exists(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is does not exist.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void NotExists(ParameterObject parameterObject);
//
//    /**
//     * Checks if the control exists.
//     *
//     * @param parameterObject The parameter object.
//     * @return The wether or not the control exists.
//     */
//    boolean ControlExists(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element exists and is not visible.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void NotVisibleExists(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is enabled.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Enabled(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is not enabled.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void NotEnabled(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is selected.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void Selected(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is not selected.
//     *
//     * @param parameterObject The element to check.
//     */
//    void NotSelected(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has an option.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasOption(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has a certain number of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasNumberOfOptions(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has a set of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasOptions(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has a set of options in order.
//     *
//     * @param parameterObject The options to check for.
//     */
//    void HasOptionsInOrder(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element does not have a certain option.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DoesNotHaveOption(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element does not have a set of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DoesNotHaveOptions(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Has(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element does not have the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DoesNotHave(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasPartial(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element does not have the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DoesNotHavePartial(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element only has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasOnly(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element has comparable options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void HasComparableOptions(ParameterObject parameterObject);
//
//    /**
//     * Checks if something is a certain value.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void Is(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is like a certain value.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Like(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is not like a certain value.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void NotLike(ParameterObject parameterObject);
//
//    /**
//     * Captures an image.
//     *
//     * @param parameterObject The element to get an image of.
//     */
//    void CaptureImage(ParameterObject parameterObject);
//
//    /**
//     * Gets the scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The current scroll position.
//     */
//    int GetScrollPosition(ParameterObject parameterObject);
//
//    /**
//     * Sets the scroll position.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void SetScrollPosition(ParameterObject parameterObject);
//
//    /**
//     * Gets the maximum scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The maximum scroll position.
//     */
//    int GetMaximumScrollPosition(ParameterObject parameterObject);
//
//    /**
//     * Gets the minimum scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The minimum scroll position.
//     */
//    int GetMinimumScrollPosition(ParameterObject parameterObject);
//
//    /**
//     * Wait until a control exists.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void WaitForControlExist(ParameterObject parameterObject);
//
//    /**
//     * Wait until the parent element contains the child.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void WaitForControlContains(ParameterObject parameterObject);
//
//    /**
//     * Blur Method.
//     *
//     * @param parameterObject Parmaeter OBject.
//     */
//    void Blur(ParameterObject parameterObject);
//
//    /**
//     * SendKeys Method.
//     *
//     * @param frameworkElement Parameter Object.
//     */
//    void SendKeys(ParameterObject frameworkElement);
//
//    /**
//     * AcceptAlert Method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void AcceptAlert(ParameterObject parameterObject);
//
//    /**
//     * Dismiss Alert Method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void DismissAlert(ParameterObject parameterObject);
//
//    /**
//     * Verify Altert Command.
//     *
//     * @param parameterObject Parameter object.
//     */
//    void VerifyAlertExists(ParameterObject parameterObject);
//
//    /**
//     * Verify Alert Not Exists Command.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void VerifyAlertNotExists(ParameterObject parameterObject);
//
//    /**
//     * Get alert text.
//     *
//     * @param parameterObject Parameter Object.
//     * @return Alert TExt.
//     */
//    String GetAlertText(ParameterObject parameterObject);
//
//    /**
//     * Send Keys to alert.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void SendKeysToAlert(ParameterObject parameterObject);
//
//    /**
//     * Closes window.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Close(ParameterObject parameterObject);
//
//    /**
//     * Quits the thing.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void Quit(ParameterObject parameterObject);
//
//    /**
//     * Adds a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void AddCookie(ParameterObject parameterObject);
//
//    /**
//     * Deletes all cookies.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DeleteAllCookiesCommand(ParameterObject parameterObject);
//
//    /**
//     * Deletes a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void DeleteCookie(ParameterObject parameterObject);
//
//    /**
//     * Gets all cookies.
//     *
//     * @param parameterObject The parameter object.
//     * @return List of cookies.
//     */
//    List<IWebCookie> GetAllCookies(ParameterObject parameterObject);
//
//    /**
//     * Gets a cookie.
//     *
//     * @param parameterObject The parameter object.
//     * @return The cookie adapter.
//     */
//    IWebCookie GetCookie(ParameterObject parameterObject);
//
//    /**
//     * Modifies a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void ModifyCookie(ParameterObject parameterObject);
//
//    /**
//     * Verify the title.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void VerifyTitle(ParameterObject parameterObject);
//
//    /**
//     * Verify the url.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void VerifyUrl(ParameterObject parameterObject);
//
//    /**
//     * Scroll to top of page.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void ScrollToTop(ParameterObject parameterObject);
//
//    /**
//     * Clears browser storage.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void ClearBrowserStorage(ParameterObject parameterObject);
//
//    /**
//     * Switches to a window by the title.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    String SwitchToWindowByTitle(ParameterObject parameterObject);
//
//    /**
//     * Switches to the main window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void SwitchToMainWindow(ParameterObject parameterObject);
//
//    /**
//     * Switches to a window by the url.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    String SwitchToWindowByUrl(ParameterObject parameterObject);
//
//    /**
//     * Checks if a window does not exist by the title.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void WindowDoesNotExistByTitle(ParameterObject parameterObject);
//
//    /**
//     * Checks if a window does not exist by the url.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void WindowDoesNotExistByUrl(ParameterObject parameterObject);
//
//    /**
//     * Maximizes the window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void Maximize(ParameterObject parameterObject);
//
//    /**
//     * Resizes the window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void Resize(ParameterObject parameterObject);
//
//    /**
//     * Goes back.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void GoBack(ParameterObject parameterObject);
//
//    /**
//     * Goes forward.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void GoForward(ParameterObject parameterObject);
//
//    /**
//     * Goes to a Url.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    String GoToUrl(ParameterObject parameterObject);
//
//    /**
//     * Refreshs the page.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void Refresh(ParameterObject parameterObject);
//
//    /**
//     * Appends a query string.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void AppendQueryString(ParameterObject parameterObject);
//
//    /**
//     * Grid Not Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void GridNotExists(ParameterObject parameterObject);
//
//    /**
//     * Grid Exists.
//     *
//     * @param parameterObject Parameter Object.
//     * @return Grid Index.
//     */
//    int GridExists(ParameterObject parameterObject);
//
//    /**
//     * Row Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void RowExists(ParameterObject parameterObject);
//
//    /**
//     * Row Not Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void RowNotExists(ParameterObject parameterObject);
//
//    /**
//     * Click by javascript.
//     *
//     * @param element Parameter Object.
//     */
//    void ClickByJavascript(ParameterObject element);
//
//    /**
//     * Set Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void SetValueByJavaScript(ParameterObject parameterObject);
//
//    /**
//     * Sets Div Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void SetDivValueByJavascript(ParameterObject parameterObject);
//
//    /**
//     * Sets Body Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void SetBodyValueByJavascript(ParameterObject parameterObject);
//
//    /**
//     * Approximately Equal.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void ApproximatelyEqual(ParameterObject parameterObject);
//
//    /**
//     * Not visible.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void IsNotVisible(ParameterObject parameterObject);
//
//    /**
//     * Refresh frame.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void RefreshFrame(ParameterObject parameterObject);
//
//    /**
//     * Gets the browser type.
//     *
//     * @param parameterObject The paramter object.
//     * @return The browser type.
//     */
//    BrowserType GetBrowserType(ParameterObject parameterObject);
//
//    /**
//     * Gets client rects.
//     *
//     * @param parameterObject The paramter object.
//     * @return A client rect.
//     */
//    ClientRects GetClientRects(ParameterObject parameterObject);
//
//    /**
//     * Gets the element attribute.
//     *
//     * @param parameterObject The paramter object.
//     * @return The element attribute.
//     */
//    String GetElementAttribute(ParameterObject parameterObject);
//
//    /**
//     * Edits the menu navigation scroll.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void EditMenuNavigationScroll(ParameterObject parameterObject);
//
//    /**
//     * Fires change event.
//     *
//     * @param parameterObject The parameter object.
//     */
//    void FireChangeEvent(ParameterObject parameterObject);
//
//    /**
//     * JQuery before or after.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void JQueryBeforeOrAfter(ParameterObject parameterObject);
//
//    /**
//     * Open file dialog.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void OpenFileDialog(ParameterObject parameterObject);
//
//    /**
//     * Select file dialog.
//     *
//     * @param parameterObject The paramter object.
//     */
//    void SelectFileDialog(ParameterObject parameterObject);
//
//    /**
//     * Scrolls the element into view.
//     *
//     * @param parameterObject The element to scroll.
//     */
//    void ScrollElementIntoView(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is not read only.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void NotReadOnly(ParameterObject parameterObject);
//
//    /**
//     * Checks if the element is read only.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    void ReadOnly(ParameterObject parameterObject);
//
//    /**
//     * Scrolls to the end of the page.
//     *
//     * @param parameterObject The frameworkElement.
//     */
//    void ScrollToEnd(ParameterObject parameterObject);
//
//    String GetTitle(ParameterObject parameterObject);
//
//    URL GetURL(ParameterObject parameterObject);
//
//    Collection<String> GetWindowHandles(ParameterObject parameterObject);
//
//    String GetPageSource();
//
//    Image GetScreenshot();
//
//    void SwitchToDefaultContent(ParameterObject parameterObject);
//
//    void SwitchToFrame(ParameterObject parameterObject);
//
//    void FocusWindow(ParameterObject parameterObject);
}
