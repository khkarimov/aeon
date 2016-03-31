package framework_abstraction;

import com.sun.glass.ui.Size;
import com.sun.prism.Image;
import common.BrowserType;
import common.ClientRects;
import common.exceptions.*;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.webdriver.ICookieAdapter;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The facade for the Framework Abstraction layer.
 */
public interface IFrameworkAbstractionFacade {

    /**
     * Gets the window handles of open browser windows.
     */
    Collection<String> getWindowHandles();

    /**
     * Gets the title of the current browser window.
     */
    String getTitle();

    /**
     * Gets the URL the browser is currently displaying.
     */
    URI getUrl();

    /**
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param element Framework element.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    WebElement FindIElement(ParameterObject element);

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param element Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    Collection<WebElement> FindIElements(ParameterObject element);

    /**
     * Clears the content of the element.
     *
     * @param element Framework element.
     */
    void ClearElement(ParameterObject element);

    /**
     * Checks if the element exists.
     *
     * @param parameterObject The parameter object.
     */
    void Exists(ParameterObject parameterObject);

    /**
     * Checks if the element exists.
     *
     * @param parameterObject The parameter object.
     */
    void NotExists(ParameterObject parameterObject);

    /**
     * Sets the value of the element.
     *
     * @param element Framework element.
     */
    void Set(ParameterObject element);

    /**
     * Checks if a value is equal to an expected value.
     *
     * @param element Framework element.
     */
    void Is(ParameterObject element);

    /**
     * Checks if a value is like an expected value.
     *
     * @param element Framework element.
     */
    void Like(ParameterObject element);

    /**
     * Checks if a value is not like an expected value.
     *
     * @param element Framework element.
     */
    void NotLike(ParameterObject element);

    /**
     * Checks if the options are present.
     *
     * @param element Framework element.
     */
    void HasOptions(ParameterObject element);

    /**
     * Checks if the number of options are present.
     *
     * @param element Framework element.
     */
    void HasNumberOfOptions(ParameterObject element);

    /**
     * Checks if the options are present in order.
     *
     * @param element Framework element.
     */
    void HasOptionsInOrder(ParameterObject element);

    /**
     * Checks if the options are present.
     *
     * @param element Framework element.
     */
    void DoesNotHaveOptions(ParameterObject element);

    /**
     * Checks if the messages are present.
     *
     * @param element Framework element.
     */
    void Has(ParameterObject element);

    /**
     * Checks if the messages are not present.
     *
     * @param element Framework element.
     */
    void DoesNotHave(ParameterObject element);

    /**
     * Checks if the messages are present.
     *
     * @param element Framework element.
     */
    void HasPartial(ParameterObject element);

    /**
     * Checks if the messages are not present.
     *
     * @param element Framework element.
     */
    void DoesNotHavePartial(ParameterObject element);

    /**
     * Checks if only the messages are present.
     *
     * @param element Framework element.
     */
    void HasOnly(ParameterObject element);

    /**
     * Checks if the comparable options are present.
     *
     * @param element Framework element.
     */
    void HasComparableOptions(ParameterObject element);

    /**
     * Gets the element tag name.
     *
     * @param element The framework element.
     * @return The element tag name.
     */
    String GetElementTagName(ParameterObject element);

    /**
     * Gets the location of the element.
     *
     * @param element Framework element.
     * @return The location of the element.
     */
    Point GetElementLocation(ParameterObject element);

    /**
     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param element Framework element.
     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     */
    String GetElementText(ParameterObject element);

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param element Framework element.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    String GetElementAttribute(ParameterObject element);

    /**
     * Checks whether or not the element is enabled.
     *
     * @param element Framework element.
     */
    void IsElementEnabled(ParameterObject element);

    /**
     * Checks whether or not the element is enabled.
     *
     * @param element Framework element.
     */
    void IsElementNotEnabled(ParameterObject element);

    /**
     * Gets a value indicating whether or not the element is selected.
     *
     * @param element Framework element.
     */
    void IsElementSelected(ParameterObject element);

    /**
     * Gets a value indicating whether or not the element is not selected.
     *
     * @param element Framework element.
     */
    void IsElementNotSelected(ParameterObject element);

    /**
     * Gets a value indicating whether or not the element is visible.
     *
     * @param element Framework element.
     */
    void IsElementVisible(ParameterObject element);

    /**
     * Mouses over element.
     *
     * @param element Framework element.
     */
    void MouseOver(ParameterObject element);

    /**
     * Mouses out of element.
     *
     * @param element Framework element.
     */
    void MouseOutOf(ParameterObject element);

    /**
     * Simulates typing text into the element.
     *
     * @param element Framework element.
     */
    void SendKeysToElement(ParameterObject element);

    /**
     * Simulates typing text into the element.
     *
     * @param element Framework element.
     */
    void SendKeysToElement2(ParameterObject element);

    /**
     * Clicks the element.
     *
     * @param element Framework element.
     */
    void ClickElement(ParameterObject element);

    /**
     * Holds a click for the duration specified.
     *
     * @param element Framework element.
     */
    void ClickAndHoldElement(ParameterObject element);

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param element Framework element.
     */
    void ClickElementAtOffset(ParameterObject element);

    /**
     * Clicks the element using javascript.
     *
     * @param element Parameter Object.
     */
    void ClickByJavaScript(ParameterObject element);

    /**
     * Right clicks the element.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    void RightClickElement(ParameterObject element);

    /**
     * Double clicks the element.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    void DoubleClickElement(ParameterObject element);

    /**
     * Drags the element and drops it in the target.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    void DragAndDrop(ParameterObject element);

    /**
     * Scrolls to the top of the page.
     *
     * @param element Framework element.
     */
    void ScrollToTop(ParameterObject element);

    /**
     * Scrolls to the end of the page.
     *
     * @param element The ParameterObject element.
     */
    void ScrollToEnd(ParameterObject element);

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param element Framework element.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    String GetElementAttributeOrText(ParameterObject element);

    /**
     * Opens element of type input type=file.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    void OpenFileDialog(ParameterObject element);

    /**
     * Load a new web page in the current browser window.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    String GoToUrl(ParameterObject element);

    /**
     * Finds the first web element using the given method.
     *
     * @param element Framework element.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebElement FindElement(ParameterObject element);

    /**
     * Finds all web elements within the current context
     * using the given mechanism.
     *
     * @param element Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see>
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<WebElement> FindElements(ParameterObject element);

    /**
     * Selects the first frame using the given method.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException    If <paramref name="element"/> is <see langword="null"/>.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void SwitchToFrame(ParameterObject element);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByTitle(ParameterObject element);

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void SwitchToWindowByHandle(ParameterObject element);

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByUrl(ParameterObject element);

    /**
     * Switches the focus of future commands for this driver to the main window.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void SwitchToMainWindow(ParameterObject element);

    /**
     * Checks for the absence of a window by title. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void WindowDoesNotExistByTitle(ParameterObject element);

    /**
     * Checks for the absence of a window by url. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void WindowDoesNotExistByUrl(ParameterObject element);

    /**
     * Close the current window, quitting the browser if it is the last window currently open.
     *
     * @param element Framework element.
     */
    void Close(ParameterObject element);

    /**
     * Quits this driver, closing every associated window.
     *
     * @param element Framework element.
     */
    void Quit(ParameterObject element);

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @param parameterObject Parameter Object.
     * @throws NoAlertException If the alert does not exist.
     */
    void VerifyAlertExists(ParameterObject parameterObject);

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @param parameterObject Parameter Object.
     * @throws AlertExistsException If the alert exists.
     */
    void VerifyAlertNotExists(ParameterObject parameterObject);

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @param parameterObject Parameter Object.
     * @return The value of the text in the alert.
     */
    String GetAlertText(ParameterObject parameterObject);

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param element Framework element.
     */
    void SendKeysToAlert(ParameterObject element);

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     * <p>
     * /// @param element Parameter Object.
     */
    void AcceptAlert(ParameterObject element);

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     * <p>
     * /// @param element Parameter Object.
     */
    void DismissAlert(ParameterObject element);

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param element Framework element.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object ExecuteScript(ParameterObject element);

    /**
     * Clears browser storage.
     *
     * @param element Framework element.
     */
    void ClearBrowserStorage(ParameterObject element);

    /**
     * Move back a single entry in the browser's history.
     *
     * @param element Framework element.
     */
    void GoBack(ParameterObject element);

    /**
     * Move a single "item" forward in the browser's history.
     *
     * @param element Framework element.
     *                Does nothing if we are on the latest page viewed.
     */
    void GoForward(ParameterObject element);

    /**
     * Refreshes the current page.
     *
     * @param element Framework element.
     */
    void Refresh(ParameterObject element);

    /**
     * Maximizes the current window if it is not already maximized.
     *
     * @param element Framework element.
     */
    void Maximize(ParameterObject element);

    /**
     * Appends a query string.
     *
     * @param element Framework element.
     */
    void AppendQueryString(ParameterObject element);

    /**
     * Resizes the current window.
     *
     * @param element Framework element.
     */
    void Resize(ParameterObject element);

    /**
     * Chooses a select web element by value.
     *
     * @param element Framework element.
     */
    void ChooseSelectElementByValue(ParameterObject element);

    /**
     * Chooses a select web element by text.
     *
     * @param element Framework element.
     */
    void ChooseSelectElementByText(ParameterObject element);

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param element Framework element.
     * @return The mapped key.
     */
    String MapKeyboardKey(ParameterObject element);

    /**
     * Gets the list of options for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element.
     */
    List<WebElement> GetElementOptions(ParameterObject element);

    /**
     * Gets the list of option groups for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element inside of the specified optGroup.
     */
    List<WebElement> GetElementOptions2(ParameterObject element);

    /**
     * Gets the list of option groups for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element inside of the specified optGroup.
     */
    List<WebElement> GetElementOptions3(ParameterObject element);

    /**
     * Scrolls the element into view.
     *
     * @param element Framework element.
     */
    void ScrollElementIntoView(ParameterObject element);

    /**
     * Gets all cookies.
     *
     * @param element Framework element.
     * @return List of cookies.
     */
    ArrayList<ICookieAdapter> GetAllCookies(ParameterObject element);

    /**
     * Gets a cookie.
     *
     * @param element Framework element.
     * @return The specified cookie.
     */
    ICookieAdapter GetCookie(ParameterObject element);

    /**
     * Adds a cookie.
     *
     * @param element Framework element.
     */
    void AddCookie(ParameterObject element);

    /**
     * Deletes a cookie.
     *
     * @param element Framework element.
     */
    void DeleteCookie(ParameterObject element);

    /**
     * Deletes all cookies.
     *
     * @param element Framework element.
     */
    void DeleteAllCookies(ParameterObject element);

    /**
     * Modifies the value of a cookie.
     *
     * @param element Framework element.
     */
    void ModifyCookie(ParameterObject element);

    /**
     * Verifies the title.
     *
     * @param element Framework element.
     */
    void VerifyTitle(ParameterObject element);

    /**
     * Verifies the url.
     *
     * @param element Framework element.
     */
    void VerifyUrl(ParameterObject element);

    /**
     * Blur Method.
     *
     * @param parameterObject Parameter Object.
     */
    void Blur(ParameterObject parameterObject);

    /**
     * Grid Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    void GridNotExists(ParameterObject parameterObject);


    /**
     * Grid Exists.
     *
     * @param parameterObject Parameter Object.
     * @return Grid Index.
     */
    int GridExists(ParameterObject parameterObject);

    /**
     * Row Exists.
     *
     * @param parameterObject Parameter Object.
     */
    void RowExists(ParameterObject parameterObject);

    /**
     * Row Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    void RowNotExists(ParameterObject parameterObject);

    /**
     * Set Value by javascript.
     *
     * @param parameterObject Parameter Object.
     */
    void SetValueByJavaScript(ParameterObject parameterObject);

    /**
     * Sets DivValue by javascript.
     *
     * @param parameterObject Parameter Object.
     */
    void SetDivValueByJavaScript(ParameterObject parameterObject);

    /**
     * Sets BodyValue by Javascript.
     *
     * @param parameterObject Parameter Object.
     */
    void SetBodyValueByJavaScript(ParameterObject parameterObject);

    /**
     * Approximately Equal.
     *
     * @param parameterObject The paramter object.
     */
    void ApproximatelyEqual(ParameterObject parameterObject);

    /**
     * Not visible.
     *
     * @param parameterObject The paramter object.
     */
    void IsElementNotVisible(ParameterObject parameterObject);

    /**
     * Refresh frame.
     *
     * @param parameterObject The paramter object.
     */
    void RefreshFrame(ParameterObject parameterObject);

    /**
     * Gets the browser type.
     *
     * @param parameterObject The paramter object.
     * @return The browser type.
     */
    BrowserType GetBrowserType(ParameterObject parameterObject);

    /**
     * Gets client rects.
     *
     * @param parameterObject The paramter object.
     * @return A client rect.
     */
    ClientRects GetClientRects(ParameterObject parameterObject);

    /**
     * Edits the menu navigation scroll.
     *
     * @param parameterObject The paramter object.
     */
    void EditMenuNavigationScroll(ParameterObject parameterObject);

    /**
     * Fires change event.
     *
     * @param parameterObject The paramter object.
     */
    void FireChangeEvent(ParameterObject parameterObject);

    /**
     * JQuery before or after.
     *
     * @param parameterObject The paramter object.
     */
    void JQueryBeforeOrAfter(ParameterObject parameterObject);

    /**
     * Select file dialog.
     *
     * @param parameterObject The paramter object.
     */
    void SelectFileDialog(ParameterObject parameterObject);

    /**
     * Toggle UltiPro stuff.
     *
     * @param parameterObject The parameter object.T
     */
    void ToggleUltiProMobileMenu(ParameterObject parameterObject);

    /**
     * Control Click.
     *
     * @param parameterObject The parameter object.
     */
    void ControlClick(ParameterObject parameterObject);

    /**
     * Checks if the element has an option.
     *
     * @param parameterObject The parameter object.
     */
    void HasOption(ParameterObject parameterObject);

    /**
     * Checks if the element does not have an option.
     *
     * @param parameterObject The parameter object.
     */
    void DoesNotHaveOption(ParameterObject parameterObject);

    /**
     * Checks if an element exists but is not visible.
     *
     * @param parameterObject The parameter object.
     */
    void NotVisibleExists(ParameterObject parameterObject);

    /**
     * Waits until the control element exists.
     *
     * @param parameterObject The parameter object.
     */
    void WaitForControlExist(ParameterObject parameterObject);

    /**
     * Waits until the parent control contains a certain number of occurances of the child element.
     *
     * @param parameterObject The parameter object.
     */
    void WaitForControlContains(ParameterObject parameterObject);

    /**
     * Captures image.
     *
     * @param parameterObject Parameter object.
     */
    void CaptureImage(ParameterObject parameterObject);

    /**
     * Checks the element.
     *
     * @param parameterObject The parameter object.
     * @return Wether the check operation was successful.
     */
    boolean Check(ParameterObject parameterObject);

    /**
     * Unchecks the element.
     *
     * @param parameterObject The parameter object.
     * @return Wether the uncheck operation was successful.
     */
    boolean Uncheck(ParameterObject parameterObject);

    /**
     * Gets the scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The scroll position.
     */
    int GetScrollPosition(ParameterObject parameterObject);

    /**
     * Gets the maximum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The maximum scroll position.
     */
    int GetMaximumScrollPosition(ParameterObject parameterObject);

    /**
     * Gets the minimum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The minimum scroll position.
     */
    int GetMinimumScrollPosition(ParameterObject parameterObject);

    /**
     * Checks if the control exists.
     *
     * @param parameterObject The parameter object.
     * @return Whether or not the control exists.
     */
    boolean ControlExists(ParameterObject parameterObject);

    /**
     * Gets the bounding rectangle.
     *
     * @param parameterObject The parameter object.
     * @return The bounding rectangle rectangle.
     */
    Rectangle GetBoundingRectangle(ParameterObject parameterObject);

    /**
     * Clears the content of the element.
     *
     * @param webElement The element.
     */
    void ClearElement(WebElement webElement);

    /**
     * Gets the tag name of the element.
     *
     * @param webElement The element.
     * @return The tag name of the element.
     */
    String GetElementTagName(WebElement webElement);

    /**
     * Gets the location of the element.
     *
     * @param webElement The element.
     * @return The location of the element.
     */
    Point GetElementLocation(WebElement webElement);

    /**
     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param webElement The element.
     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     */
    String GetElementText(WebElement webElement);

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param webElement    The element.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    String GetElementAttribute(WebElement webElement, String attributeName);

    /**
     * Gets a value indicating whether or not the element is enabled.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is enabled.
     */
    boolean IsElementEnabled(WebElement webElement);

    /**
     * Gets a value indicating whether or not the element is selected.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is selected.
     */
    boolean IsElementSelected(WebElement webElement);

    /**
     * Gets a value indicating whether or not the element is displayed.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is displayed.
     */
    boolean IsElementDisplayed(WebElement webElement);

    /**
     * Simulates typing text into the element.
     *
     * @param webElement The element.
     * @param text       The text to type into the element.
     * @throws IllegalArgumentException If <paramref name="text"/> is <see langword="null"/>.
     */
    void SendKeysToElement(WebElement webElement, String text);

    /**
     * Simulates typing text into the element.
     *
     * @param webElement  The element.
     * @param keyboardKey The key to type into the element.
     */
    void SendKeysToElement(WebElement webElement, Keys keyboardKey);

    /**
     * Clicks the element.
     *
     * @param webElement The element.
     */
    void ClickElement(WebElement webElement);

    /**
     * Holds a click for the duration specified.
     *
     * @param webElement The element to click.
     * @param duration   Click for at least this long (in milliseconds).
     */
    void ClickAndHoldElement(WebElement webElement, int duration);

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param webElement The element to click.
     * @param x          The x offset.
     * @param y          The y offset.
     */
    void ClickElementAtOffset(WebElement webElement, int x, int y);

    /**
     * Right clicks the element.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    void RightClickElement(IBy selector);

    /**
     * Double clicks the element.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    void DoubleClickElement(IBy selector);

    /**
     * Drags the element and drops it in the target.
     *
     * @param dropElement The element.
     * @param dropTarget  The target.
     * @throws IllegalArgumentException If <paramref name="dropElement"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="dropTarget"/> is <see langword="null"/>.
     */
    void DragAndDrop(IBy dropElement, IBy dropTarget);

    /**
     * Scrolls to the top of the page.
     */
    void ScrollToTop();

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param webElement    The element.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    String GetElementAttributeOrText(WebElement webElement, String attributeName);

    /**
     * Opens element of type input type=file.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    void OpenFileDialog(IBy selector);

    /**
     * Load a new web page in the current browser window.
     *
     * @param url The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="url"/> is <see langword="null"/>.
     */
    String GoToUrl(URI url);

    /**
     * Finds the first web element using the given method.
     *
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    WebElement FindElement(IBy findBy);

    /**
     * Finds all web elements within the current context
     * using the given mechanism.
     *
     * @param findBy The locating mechanism to use.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see>
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<WebElement> FindElements(IBy findBy);

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     */
    void SwitchToDefaultContent();

    /**
     * Selects the first frame using the given method.
     *
     * @param findBy The locating mechanism to use.
     * @throws IllegalArgumentException    If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void SwitchToFrame(IBy findBy);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param windowTitle The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByTitle(String windowTitle);

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param handle The handle of the window to select.
     * @throws IllegalArgumentException If <paramref name="handle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="handle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void SwitchToWindowByHandle(String handle);

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param value The URL to which to switch focus.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="value"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="value"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByUrl(String value);

    /**
     * Checks for the absence of a window by title. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param windowTitle    The title of the window to select.
     * @param currentHandler The current handler.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void WindowDoesNotExistByTitle(String windowTitle, String currentHandler);

    /**
     * Checks for the absence of a window by url. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param url            The url of the window to select.
     * @param currentHandler The current handler.
     * @throws IllegalArgumentException If <paramref name="url"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="url"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void WindowDoesNotExistByUrl(String url, String currentHandler);

    /**
     * Close the current window, quitting the browser if it is the last window currently open.
     */
    void CloseCurrentWindow();

    /**
     * Quits this driver, closing every associated window.
     */
    void QuitDriver();

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
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @return The value of the text in the alert.
     */
    String GetAlertText();

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param keysToSend The keystrokes to send.
     */
    void SendKeysToAlert(String keysToSend);

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     */
    void AcceptAlert();

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     */
    void DismissAlert();

    /**
     * Focuses the currently selected window.
     */
    void FocusWindow();

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object ExecuteScript(String script, Object[]... args);

    /**
     * Move back a single entry in the browser's history.
     */
    void MoveBrowserBack();

    /**
     * Move a single "item" forward in the browser's history.
     * <p>
     * Does nothing if we are on the latest page viewed.
     */
    void MoveBrowserForward();

    /**
     * Refreshes the current page.
     */
    void RefreshPage();

    /**
     * Maximizes the current window if it is not already maximized.
     */
    void MaximizeCurrentWindow();

    /**
     * Resizes the current window.
     *
     * @param size The new browser size.
     */
    void ResizeCurrentWindow(Size size);

    /**
     * Chooses a select web element by value.
     *
     * @param webElement The web element.
     * @param value      The value to choose.
     */
    void ChooseSelectElementByValue(WebElement webElement, String value);

    /**
     * Chooses a select web element by text.
     *
     * @param webElement The web element.
     * @param text       The text to choose.
     */
    void ChooseSelectElementByText(WebElement webElement, String text);

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param key The key to map.
     * @return The mapped key.
     */
    String MapKeyboardKey(Keys key);

    /**
     * Gets the list of options for the element.
     *
     * @param webElement The element.
     * @return The list of options for the element.
     */
    List<WebElement> GetElementOptions(WebElement webElement);

    /**
     * Gets the list of option groups for the element.
     *
     * @param webElement The element.
     * @param optGroup   The options group to search within.
     * @return The list of options for the element inside of the specified optGroup.
     */
    List<WebElement> GetElementOptions(WebElement webElement, String optGroup);

    /**
     * Gets the list of option groups for the element.
     *
     * @param webElement The element.
     * @param optGroup   The options group to search within.
     * @param attribute  The HTML attribute to identify the option group by.
     * @return The list of options for the element inside of the specified optGroup.
     */
    List<WebElement> GetElementOptions(WebElement webElement, String optGroup, String attribute);

    /**
     * Get the Screenshot of the currently focus browser window.
     *
     * @return Image object.
     */
    Image GetScreenshot();

    /**
     * Gets the Source of the current browser window.
     *
     * @return The current page's source as a string.
     */
    String GetPageSource();

    /**
     * Scrolls the element into view.
     *
     * @param webElement The element.
     */
    void ScrollElementIntoView(WebElement webElement);

    /**
     * Refreshes an iframe.
     */
    void RefreshFrame();

    /**
     * Gets all cookies.
     *
     * @return List of cookies.
     */
    ArrayList<ICookieAdapter> GetAllCookies();

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    ICookieAdapter GetCookie(String name);

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    void AddCookie(ICookieAdapter cookie);

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be deleted.
     */
    void DeleteCookie(String name);

    /**
     * Deletes all cookies.
     */
    void DeleteAllCookies();

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    void ModifyCookie(String name, String value);

    /**
     * Checks if empty.
     *
     * @param parameterObject Parameter Object.
     */
    void isEmpty(ParameterObject parameterObject);

    /**
     * Sets the scroll position of the element.
     *
     * @param parameterObject Parameter Object.
     */
    void SetScrollPosition(ParameterObject parameterObject);

    /**
     * Checks if the element is not read only.
     *
     * @param parameterObject Parameter Object.
     */
    void NotReadOnly(ParameterObject parameterObject);

    /**
     * Checks if the elements is a read only element.
     *
     * @param parameterObject Parameter Object.
     */
    void ReadOnly(ParameterObject parameterObject);
}