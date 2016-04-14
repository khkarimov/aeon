package echo.core.framework_abstraction;

import com.sun.glass.ui.Size;
import com.sun.prism.*;
import com.sun.prism.Image;
import echo.core.command_execution.AutomationInfo;
import echo.core.common.BrowserType;
import echo.core.common.ClientRects;
import echo.core.common.exceptions.*;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.webdriver.ICookieAdapter;
import echo.core.framework_abstraction.webdriver.IKeyboardMapper;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;
import echo.core.framework_interaction.IFrameworkAdapter;
import org.openqa.selenium.Keys;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * The facade for the Framework Abstraction layer.
 */
public final class FrameworkAbstractionFacade implements IFrameworkAbstractionFacade {
    private IKeyboardMapper keyboardMapper;
    private ISelectElementFactory selectElementFactory;
    private boolean headlessMode;
    private UUID guid = UUID.randomUUID();
    private ILog log;
    private IDriver webDriverAdapter;
    private IFrameworkAdapter adapter;

    /**
     * Initializes a new instance of the <see cref="FrameworkAbstractionFacade"/> class.
     *
     * @param keyboardMapper       The keyboard mapper.
     * @param selectElementFactory The select element factory.
     * @param headlessMode         The headless mode.
     * @param guid                 A globally unique identifier associated with this call.
     * @param automationInfo       The automation info.
     * @param adapter              Framework adapter.
     */
    public FrameworkAbstractionFacade(IKeyboardMapper keyboardMapper, ISelectElementFactory selectElementFactory, boolean headlessMode, UUID guid, AutomationInfo automationInfo, IFrameworkAdapter adapter) {
        this.keyboardMapper = keyboardMapper;
        this.selectElementFactory = selectElementFactory;
        this.headlessMode = headlessMode;
        this.guid = guid;
        log = automationInfo.getLog();
        webDriverAdapter = automationInfo.getDriverAdapter();
        this.adapter = adapter;
    }

    /**
     * Gets the window handles of open browser windows.
     */
    public Collection<String> getWindowHandles() {
        return webDriverAdapter.GetWindowHandles(guid);
    }

    /**
     * Gets the title of the current browser window.
     */
    public String getTitle() {
        return webDriverAdapter.GetTitle(guid);
    }

    /**
     * Gets the URL the browser is currently displaying.
     */
    public URL getUrl() {
        return webDriverAdapter.GetUrl(guid);
    }

    /**
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param element Framework element.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public WebElement FindIElement(ParameterObject element) {
        return null;
    }

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param element Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public Collection<WebElement> FindIElements(ParameterObject element) {
        return null;
    }

    /**
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param frameworkElement Framework element.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     */
    public WebElement FindElement(ParameterObject frameworkElement) {
        return new WebElement(frameworkElement.getWeb().getWebElement().getWebElementAdapter()
                .FindElement(guid, frameworkElement.getWeb().getFindIBy()), frameworkElement.getWeb().getFindIBy());
    }

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param frameworkElement Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="frameworkElement"/> is <see langword="null"/>.
     */
    public Collection<WebElement> FindElements(ParameterObject frameworkElement) {
        return new ArrayList<>(frameworkElement.getWeb().getWebElement().getWebElementAdapter()
                .FindElements(guid, frameworkElement.getWeb().getFindIBy())
                .stream().map(x -> new WebElement(x, frameworkElement.getWeb().getFindIBy()))
                .collect(Collectors.toList()));
    }

    /**
     * Selects the first frame using the given method.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException    If <paramref name="element"/> is <see langword="null"/>.
     * @throws UnsupportedElementException If the element is not supported.
     */
    @Override
    public void SwitchToFrame(ParameterObject element) {

    }

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public String SwitchToWindowByTitle(ParameterObject element) {
        return null;
    }

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public void SwitchToWindowByHandle(ParameterObject element) {

    }

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public String SwitchToWindowByUrl(ParameterObject element) {
        return null;
    }

    /**
     * Switches the focus of future commands for this driver to the main window.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public void SwitchToMainWindow(ParameterObject element) {

    }

    /**
     * Checks for the absence of a window by title. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public void WindowDoesNotExistByTitle(ParameterObject element) {

    }

    /**
     * Checks for the absence of a window by url. The function tries to switch to the window that does not exist.
     * If the window does not exist, then it switches back to the current window handle.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public void WindowDoesNotExistByUrl(ParameterObject element) {

    }

    /**
     * Close the current window, quitting the browser if it is the last window currently open.
     *
     * @param element Framework element.
     */
    @Override
    public void Close(ParameterObject element) {

    }

    /**
     * Quits this driver, closing every associated window.
     *
     * @param element Framework element.
     */
    @Override
    public void Quit(ParameterObject element) {

    }

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @param parameterObject Parameter Object.
     * @throws NoAlertException If the alert does not exist.
     */
    @Override
    public void VerifyAlertExists(ParameterObject parameterObject) {

    }

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @param parameterObject Parameter Object.
     * @throws AlertExistsException If the alert exists.
     */
    @Override
    public void VerifyAlertNotExists(ParameterObject parameterObject) {

    }

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @param parameterObject Parameter Object.
     * @return The value of the text in the alert.
     */
    @Override
    public String GetAlertText(ParameterObject parameterObject) {
        return null;
    }

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param element Framework element.
     */
    @Override
    public void SendKeysToAlert(ParameterObject element) {

    }

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     * <p>
     * /// @param element Parameter Object.
     *
     * @param element
     */
    @Override
    public void AcceptAlert(ParameterObject element) {

    }

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     * <p>
     * /// @param element Parameter Object.
     *
     * @param element
     */
    @Override
    public void DismissAlert(ParameterObject element) {

    }

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param element Framework element.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    @Override
    public Object ExecuteScript(ParameterObject element) {
        return null;
    }

    /**
     * Clears browser storage.
     *
     * @param element Framework element.
     */
    @Override
    public void ClearBrowserStorage(ParameterObject element) {

    }

    /**
     * Move back a single entry in the browser's history.
     *
     * @param element Framework element.
     */
    @Override
    public void GoBack(ParameterObject element) {

    }

    /**
     * Move a single "item" forward in the browser's history.
     *
     * @param element Framework element.
     *                Does nothing if we are on the latest page viewed.
     */
    @Override
    public void GoForward(ParameterObject element) {

    }

    /**
     * Refreshes the current page.
     *
     * @param element Framework element.
     */
    @Override
    public void Refresh(ParameterObject element) {

    }

    /**
     * Maximizes the current window if it is not already maximized.
     *
     * @param element Framework element.
     */
    @Override
    public void Maximize(ParameterObject element) {

    }

    /**
     * Appends a query string.
     *
     * @param element Framework element.
     */
    @Override
    public void AppendQueryString(ParameterObject element) {

    }

    /**
     * Resizes the current window.
     *
     * @param element Framework element.
     */
    @Override
    public void Resize(ParameterObject element) {

    }

    /**
     * Chooses a select web element by value.
     *
     * @param element Framework element.
     */
    @Override
    public void ChooseSelectElementByValue(ParameterObject element) {

    }

    /**
     * Chooses a select web element by text.
     *
     * @param element Framework element.
     */
    @Override
    public void ChooseSelectElementByText(ParameterObject element) {

    }

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param element Framework element.
     * @return The mapped key.
     */
    @Override
    public String MapKeyboardKey(ParameterObject element) {
        return null;
    }

    /**
     * Gets the list of options for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element.
     */
    @Override
    public List<WebElement> GetElementOptions(ParameterObject element) {
        return null;
    }

    /**
     * Gets the list of option groups for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element inside of the specified optGroup.
     */
    @Override
    public List<WebElement> GetElementOptions2(ParameterObject element) {
        return null;
    }

    /**
     * Gets the list of option groups for the element.
     *
     * @param element Framework element.
     * @return The list of options for the element inside of the specified optGroup.
     */
    @Override
    public List<WebElement> GetElementOptions3(ParameterObject element) {
        return null;
    }

    /**
     * Scrolls the element into view.
     *
     * @param element Framework element.
     */
    @Override
    public void ScrollElementIntoView(ParameterObject element) {

    }

    /**
     * Gets all cookies.
     *
     * @param element Framework element.
     * @return List of cookies.
     */
    @Override
    public ArrayList<ICookieAdapter> GetAllCookies(ParameterObject element) {
        return null;
    }

    /**
     * Gets a cookie.
     *
     * @param element Framework element.
     * @return The specified cookie.
     */
    @Override
    public ICookieAdapter GetCookie(ParameterObject element) {
        return null;
    }

    /**
     * Adds a cookie.
     *
     * @param element Framework element.
     */
    @Override
    public void AddCookie(ParameterObject element) {

    }

    /**
     * Deletes a cookie.
     *
     * @param element Framework element.
     */
    @Override
    public void DeleteCookie(ParameterObject element) {

    }

    /**
     * Deletes all cookies.
     *
     * @param element Framework element.
     */
    @Override
    public void DeleteAllCookies(ParameterObject element) {

    }

    /**
     * Modifies the value of a cookie.
     *
     * @param element Framework element.
     */
    @Override
    public void ModifyCookie(ParameterObject element) {

    }

    /**
     * Verifies the title.
     *
     * @param element Framework element.
     */
    @Override
    public void VerifyTitle(ParameterObject element) {

    }

    /**
     * Verifies the url.
     *
     * @param element Framework element.
     */
    @Override
    public void VerifyUrl(ParameterObject element) {

    }

    /**
     * Blur Method.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void Blur(ParameterObject parameterObject) {

    }

    /**
     * Grid Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void GridNotExists(ParameterObject parameterObject) {

    }

    /**
     * Grid Exists.
     *
     * @param parameterObject Parameter Object.
     * @return Grid Index.
     */
    @Override
    public int GridExists(ParameterObject parameterObject) {
        return 0;
    }

    /**
     * Row Exists.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void RowExists(ParameterObject parameterObject) {

    }

    /**
     * Row Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void RowNotExists(ParameterObject parameterObject) {

    }

    /**
     * Set Value by javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetValueByJavaScript(ParameterObject parameterObject) {

    }

    /**
     * Sets DivValue by javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetDivValueByJavaScript(ParameterObject parameterObject) {

    }

    /**
     * Sets BodyValue by Javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetBodyValueByJavaScript(ParameterObject parameterObject) {

    }

    /**
     * Approximately Equal.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void ApproximatelyEqual(ParameterObject parameterObject) {

    }

    /**
     * Not visible.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void IsElementNotVisible(ParameterObject parameterObject) {

    }

    /**
     * Refresh frame.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void RefreshFrame(ParameterObject parameterObject) {

    }

    /**
     * Gets the browser type.
     *
     * @param parameterObject The paramter object.
     * @return The browser type.
     */
    @Override
    public BrowserType GetBrowserType(ParameterObject parameterObject) {
        return null;
    }

    /**
     * Gets client rects.
     *
     * @param parameterObject The paramter object.
     * @return A client rect.
     */
    @Override
    public ClientRects GetClientRects(ParameterObject parameterObject) {
        return null;
    }

    /**
     * Edits the menu navigation scroll.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void EditMenuNavigationScroll(ParameterObject parameterObject) {

    }

    /**
     * Fires change event.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void FireChangeEvent(ParameterObject parameterObject) {

    }

    /**
     * JQuery before or after.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void JQueryBeforeOrAfter(ParameterObject parameterObject) {

    }

    /**
     * Select file dialog.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void SelectFileDialog(ParameterObject parameterObject) {

    }

    /**
     * Toggle UltiPro stuff.
     *
     * @param parameterObject The parameter object.T
     */
    @Override
    public void ToggleUltiProMobileMenu(ParameterObject parameterObject) {

    }

    /**
     * Control Click.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void ControlClick(ParameterObject parameterObject) {

    }

    /**
     * Checks if the element has an option.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasOption(ParameterObject parameterObject) {

    }

    /**
     * Checks if the element does not have an option.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void DoesNotHaveOption(ParameterObject parameterObject) {

    }

    /**
     * Checks if an element exists but is not visible.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void NotVisibleExists(ParameterObject parameterObject) {

    }

    /**
     * Waits until the control element exists.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void WaitForControlExist(ParameterObject parameterObject) {

    }

    /**
     * Waits until the parent control contains a certain number of occurances of the child element.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void WaitForControlContains(ParameterObject parameterObject) {

    }

    /**
     * Captures image.
     *
     * @param parameterObject Parameter object.
     */
    @Override
    public void CaptureImage(ParameterObject parameterObject) {

    }

    /**
     * Checks the element.
     *
     * @param parameterObject The parameter object.
     * @return Wether the check operation was successful.
     */
    @Override
    public boolean Check(ParameterObject parameterObject) {
        return false;
    }

    /**
     * Unchecks the element.
     *
     * @param parameterObject The parameter object.
     * @return Wether the uncheck operation was successful.
     */
    @Override
    public boolean Uncheck(ParameterObject parameterObject) {
        return false;
    }

    /**
     * Gets the scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The scroll position.
     */
    @Override
    public int GetScrollPosition(ParameterObject parameterObject) {
        return 0;
    }

    /**
     * Gets the maximum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The maximum scroll position.
     */
    @Override
    public int GetMaximumScrollPosition(ParameterObject parameterObject) {
        return 0;
    }

    /**
     * Gets the minimum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The minimum scroll position.
     */
    @Override
    public int GetMinimumScrollPosition(ParameterObject parameterObject) {
        return 0;
    }

    /**
     * Checks if the control exists.
     *
     * @param parameterObject The parameter object.
     * @return Whether or not the control exists.
     */
    @Override
    public boolean ControlExists(ParameterObject parameterObject) {
        return false;
    }

    /**
     * Gets the bounding rectangle.
     *
     * @param parameterObject The parameter object.
     * @return The bounding rectangle rectangle.
     */
    @Override
    public Rectangle GetBoundingRectangle(ParameterObject parameterObject) {
        return null;
    }

    /**
     * Clears the content of the element.
     *
     * @param webElement The element.
     */
    @Override
    public void ClearElement(WebElement webElement) {

    }

    /**
     * Gets the tag name of the element.
     *
     * @param webElement The element.
     * @return The tag name of the element.
     */
    @Override
    public String GetElementTagName(WebElement webElement) {
        return null;
    }

    /**
     * Gets the location of the element.
     *
     * @param webElement The element.
     * @return The location of the element.
     */
    @Override
    public Point GetElementLocation(WebElement webElement) {
        return null;
    }

    /**
     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param webElement The element.
     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     */
    @Override
    public String GetElementText(WebElement webElement) {
        return null;
    }

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param webElement    The element.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    @Override
    public String GetElementAttribute(WebElement webElement, String attributeName) {
        return null;
    }

    /**
     * Gets a value indicating whether or not the element is enabled.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is enabled.
     */
    @Override
    public boolean IsElementEnabled(WebElement webElement) {
        return false;
    }

    /**
     * Gets a value indicating whether or not the element is selected.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is selected.
     */
    @Override
    public boolean IsElementSelected(WebElement webElement) {
        return false;
    }

    /**
     * Gets a value indicating whether or not the element is displayed.
     *
     * @param webElement The element.
     * @return A value indicating whether or not the element is displayed.
     */
    @Override
    public boolean IsElementDisplayed(WebElement webElement) {
        return false;
    }

    /**
     * Simulates typing text into the element.
     *
     * @param webElement The element.
     * @param text       The text to type into the element.
     * @throws IllegalArgumentException If <paramref name="text"/> is <see langword="null"/>.
     */
    @Override
    public void SendKeysToElement(WebElement webElement, String text) {

    }

    /**
     * Simulates typing text into the element.
     *
     * @param webElement  The element.
     * @param keyboardKey The key to type into the element.
     */
    @Override
    public void SendKeysToElement(WebElement webElement, Keys keyboardKey) {

    }

    /**
     * Clicks the element.
     *
     * @param webElement The element.
     */
    @Override
    public void ClickElement(WebElement webElement) {

    }

    /**
     * Holds a click for the duration specified.
     *
     * @param webElement The element to click.
     * @param duration   Click for at least this long (in milliseconds).
     */
    @Override
    public void ClickAndHoldElement(WebElement webElement, int duration) {

    }

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param webElement The element to click.
     * @param x          The x offset.
     * @param y          The y offset.
     */
    @Override
    public void ClickElementAtOffset(WebElement webElement, int x, int y) {

    }

    /**
     * Right clicks the element.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    @Override
    public void RightClickElement(IBy selector) {

    }

    /**
     * Double clicks the element.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    @Override
    public void DoubleClickElement(IBy selector) {

    }

    /**
     * Drags the element and drops it in the target.
     *
     * @param dropElement The element.
     * @param dropTarget  The target.
     * @throws IllegalArgumentException If <paramref name="dropElement"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="dropTarget"/> is <see langword="null"/>.
     */
    @Override
    public void DragAndDrop(IBy dropElement, IBy dropTarget) {

    }

    /**
     * Scrolls to the top of the page.
     */
    @Override
    public void ScrollToTop() {

    }

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param webElement    The element.
     * @param attributeName The name of the attribute.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="attributeName"/> is <see langword="null"/>.
     */
    @Override
    public String GetElementAttributeOrText(WebElement webElement, String attributeName) {
        return null;
    }

    /**
     * Opens element of type input type=file.
     *
     * @param selector The selector for the element.
     * @throws IllegalArgumentException If <paramref name="selector"/> is <see langword="null"/>.
     */
    @Override
    public void OpenFileDialog(IBy selector) {

    }

    /**
     * Load a new web page in the current browser window.
     *
     * @param url The URL to load. It is best to use a fully qualified URL.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="url"/> is <see langword="null"/>.
     */
    @Override
    public String GoToUrl(URI url) {
        return null;
    }

    /**
     * Finds the first web element using the given method.
     *
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    @Override
    public WebElement FindElement(IBy findBy) {
        return null;
    }

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
    @Override
    public Collection<WebElement> FindElements(IBy findBy) {
        return null;
    }

    /**
     * Selects either the first frame on the page or the main document when a page contains iframes.
     */
    @Override
    public void SwitchToDefaultContent() {

    }

    /**
     * Selects the first frame using the given method.
     *
     * @param findBy The locating mechanism to use.
     * @throws IllegalArgumentException    If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws UnsupportedElementException If the element is not supported.
     */
    @Override
    public void SwitchToFrame(IBy findBy) {

    }

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param windowTitle The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public String SwitchToWindowByTitle(String windowTitle) {
        return null;
    }

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param handle The handle of the window to select.
     * @throws IllegalArgumentException If <paramref name="handle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="handle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public void SwitchToWindowByHandle(String handle) {

    }

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param value The URL to which to switch focus.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="value"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="value"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    @Override
    public String SwitchToWindowByUrl(String value) {
        return null;
    }

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
    @Override
    public void WindowDoesNotExistByTitle(String windowTitle, String currentHandler) {

    }

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
    @Override
    public void WindowDoesNotExistByUrl(String url, String currentHandler) {

    }

    /**
     * Close the current window, quitting the browser if it is the last window currently open.
     */
    @Override
    public void CloseCurrentWindow() {

    }

    /**
     * Quits this driver, closing every associated window.
     */
    @Override
    public void QuitDriver() {

    }

    /**
     * Checks whether an active modal dialog for this particular driver instance exists.
     *
     * @throws NoAlertException If the alert does not exist.
     */
    @Override
    public void VerifyAlertExists() {

    }

    /**
     * Checks whether an active modal dialog for this particular driver instance does not exist.
     *
     * @throws AlertExistsException If the alert exists.
     */
    @Override
    public void VerifyAlertNotExists() {

    }

    /**
     * Checks whether the text of the currently active modal dialog for this particular driver instance equals the expected value.
     *
     * @return The value of the text in the alert.
     */
    @Override
    public String GetAlertText() {
        return null;
    }

    /**
     * Sends keys to the currently active modal dialog for this particular driver instance.
     *
     * @param keysToSend The keystrokes to send.
     */
    @Override
    public void SendKeysToAlert(String keysToSend) {

    }

    /**
     * Accepts the currently active modal dialog for this particular driver instance.
     */
    @Override
    public void AcceptAlert() {

    }

    /**
     * Dismisses the currently active modal dialog for this particular driver instance.
     */
    @Override
    public void DismissAlert() {

    }

    /**
     * Focuses the currently selected window.
     */
    @Override
    public void FocusWindow() {

    }

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    @Override
    public Object ExecuteScript(String script, Object[]... args) {
        return null;
    }

    /**
     * Move back a single entry in the browser's history.
     */
    @Override
    public void MoveBrowserBack() {

    }

    /**
     * Move a single "item" forward in the browser's history.
     * <p>
     * Does nothing if we are on the latest page viewed.
     */
    @Override
    public void MoveBrowserForward() {

    }

    /**
     * Refreshes the current page.
     */
    @Override
    public void RefreshPage() {

    }

    /**
     * Maximizes the current window if it is not already maximized.
     */
    @Override
    public void MaximizeCurrentWindow() {

    }

    /**
     * Resizes the current window.
     *
     * @param size The new browser size.
     */
    @Override
    public void ResizeCurrentWindow(Size size) {

    }

    /**
     * Chooses a select web element by value.
     *
     * @param webElement The web element.
     * @param value      The value to choose.
     */
    @Override
    public void ChooseSelectElementByValue(WebElement webElement, String value) {

    }

    /**
     * Chooses a select web element by text.
     *
     * @param webElement The web element.
     * @param text       The text to choose.
     */
    @Override
    public void ChooseSelectElementByText(WebElement webElement, String text) {

    }

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param key The key to map.
     * @return The mapped key.
     */
    @Override
    public String MapKeyboardKey(Keys key) {
        return null;
    }

    /**
     * Gets the list of options for the element.
     *
     * @param webElement The element.
     * @return The list of options for the element.
     */
    @Override
    public List<WebElement> GetElementOptions(WebElement webElement) {
        return null;
    }

    /**
     * Gets the list of option groups for the element.
     *
     * @param webElement The element.
     * @param optGroup   The options group to search within.
     * @return The list of options for the element inside of the specified optGroup.
     */
    @Override
    public List<WebElement> GetElementOptions(WebElement webElement, String optGroup) {
        return null;
    }

    /**
     * Gets the list of option groups for the element.
     *
     * @param webElement The element.
     * @param optGroup   The options group to search within.
     * @param attribute  The HTML attribute to identify the option group by.
     * @return The list of options for the element inside of the specified optGroup.
     */
    @Override
    public List<WebElement> GetElementOptions(WebElement webElement, String optGroup, String attribute) {
        return null;
    }

    /**
     * Get the Screenshot of the currently focus browser window.
     *
     * @return Image object.
     */
    @Override
    public Image GetScreenshot() {
        return null;
    }

    /**
     * Gets the Source of the current browser window.
     *
     * @return The current page's source as a string.
     */
    @Override
    public String GetPageSource() {
        return null;
    }

    /**
     * Scrolls the element into view.
     *
     * @param webElement The element.
     */
    @Override
    public void ScrollElementIntoView(WebElement webElement) {

    }

    /**
     * Refreshes an iframe.
     */
    @Override
    public void RefreshFrame() {

    }

    /**
     * Gets all cookies.
     *
     * @return List of cookies.
     */
    @Override
    public ArrayList<ICookieAdapter> GetAllCookies() {
        return null;
    }

    /**
     * Gets a cookie.
     *
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    @Override
    public ICookieAdapter GetCookie(String name) {
        return null;
    }

    /**
     * Adds a cookie.
     *
     * @param cookie The cookie to be added.
     */
    @Override
    public void AddCookie(ICookieAdapter cookie) {

    }

    /**
     * Deletes a cookie.
     *
     * @param name The name of the cookie to be deleted.
     */
    @Override
    public void DeleteCookie(String name) {

    }

    /**
     * Deletes all cookies.
     */
    @Override
    public void DeleteAllCookies() {

    }

    /**
     * Modifies the value of a cookie.
     *
     * @param name  The name of the cookie to be modified.
     * @param value The value of the cookie.
     */
    @Override
    public void ModifyCookie(String name, String value) {

    }

    /**
     * Checks if empty.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void isEmpty(ParameterObject parameterObject) {

    }

    /**
     * Sets the scroll position of the element.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetScrollPosition(ParameterObject parameterObject) {

    }

    /**
     * Checks if the element is not read only.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void NotReadOnly(ParameterObject parameterObject) {

    }

    /**
     * Checks if the elements is a read only element.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void ReadOnly(ParameterObject parameterObject) {

    }

    /**
     * Sets the value of the element.
     *
     * @param frameworkElement Framework element.
     */
    public void Set(ParameterObject frameworkElement) {
        adapter.Set(frameworkElement);
    }

    /**
     * Clears the content of the element.
     *
     * @param frameworkElement Framework element.
     */
    public void ClearElement(ParameterObject frameworkElement) {
        adapter.Clear(frameworkElement);
    }

    /**
     * Checks if the element exists.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void Exists(ParameterObject parameterObject) {

    }

    /**
     * Checks if the element exists.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void NotExists(ParameterObject parameterObject) {

    }

    /**
     * Checks if a value is the expected value.
     *
     * @param frameworkElement Framework element.
     */
    public void Is(ParameterObject frameworkElement) {
        adapter.Is(frameworkElement);
    }

    /**
     * Checks if a value is like an expected value.
     *
     * @param frameworkElement Framework element.
     */
    public void Like(ParameterObject frameworkElement) {
        adapter.Like(frameworkElement);
    }

    /**
     * Checks if a value is not like an expected value.
     *
     * @param frameworkElement Framework element.
     */
    public void NotLike(ParameterObject frameworkElement) {
        adapter.NotLike(frameworkElement);
    }

    /**
     * Checks if the options are present.
     *
     * @param frameworkElement Framework element.
     */
    public void HasOptions(ParameterObject frameworkElement) {
        adapter.HasOptions(frameworkElement);
    }

    /**
     * Checks if the number of options are present.
     *
     * @param frameworkElement Framework element.
     */
    public void HasNumberOfOptions(ParameterObject frameworkElement) {
        adapter.HasNumberOfOptions(frameworkElement);
    }

    /**
     * Checks if the options are present in order.
     *
     * @param frameworkElement Framework element.
     */
    public void HasOptionsInOrder(ParameterObject frameworkElement) {
        adapter.HasOptionsInOrder(frameworkElement);
    }

    /**
     * Checks if the options are present.
     *
     * @param frameworkElement Framework element.
     */
    public void DoesNotHaveOptions(ParameterObject frameworkElement) {
        adapter.DoesNotHaveOptions(frameworkElement);
    }

    /**
     * Checks if the messages are present.
     *
     * @param frameworkElement Framework element.
     */
    public void Has(ParameterObject frameworkElement) {
        adapter.Has(frameworkElement);
    }

    /**
     * Checks if the messages are not present.
     *
     * @param frameworkElement Framework element.
     */
    public void DoesNotHave(ParameterObject frameworkElement) {
        adapter.DoesNotHave(frameworkElement);
    }

    /**
     * Checks if the messages are present.
     *
     * @param frameworkElement Framework element.
     */
    public void HasPartial(ParameterObject frameworkElement) {
        adapter.HasPartial(frameworkElement);
    }

    /**
     * Checks if the messages are not present.
     *
     * @param frameworkElement Framework element.
     */
    public void DoesNotHavePartial(ParameterObject frameworkElement) {
        adapter.DoesNotHavePartial(frameworkElement);
    }

    /**
     * Checks if only the messages are present.
     *
     * @param frameworkElement Framework element.
     */
    public void HasOnly(ParameterObject frameworkElement) {
        adapter.HasOnly(frameworkElement);
    }

    /**
     * Checks if the comparable options are present.
     *
     * @param element Framework element.
     */
    @Override
    public void HasComparableOptions(ParameterObject element) {

    }

    /**
     * Gets the element tag name.
     *
     * @param element The framework element.
     * @return The element tag name.
     */
    @Override
    public String GetElementTagName(ParameterObject element) {
        return null;
    }

    /**
     * Gets the location of the element.
     *
     * @param element Framework element.
     * @return The location of the element.
     */
    @Override
    public Point GetElementLocation(ParameterObject element) {
        return null;
    }

    /**
     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param element Framework element.
     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     */
    @Override
    public String GetElementText(ParameterObject element) {
        return null;
    }

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param element Framework element.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public String GetElementAttribute(ParameterObject element) {
        return null;
    }

    /**
     * Checks whether or not the element is enabled.
     *
     * @param element Framework element.
     */
    @Override
    public void IsElementEnabled(ParameterObject element) {

    }

    /**
     * Checks whether or not the element is enabled.
     *
     * @param element Framework element.
     */
    @Override
    public void IsElementNotEnabled(ParameterObject element) {

    }

    /**
     * Gets a value indicating whether or not the element is selected.
     *
     * @param element Framework element.
     */
    @Override
    public void IsElementSelected(ParameterObject element) {

    }

    /**
     * Gets a value indicating whether or not the element is not selected.
     *
     * @param element Framework element.
     */
    @Override
    public void IsElementNotSelected(ParameterObject element) {

    }

    /**
     * Gets a value indicating whether or not the element is visible.
     *
     * @param element Framework element.
     */
    @Override
    public void IsElementVisible(ParameterObject element) {

    }

    /**
     * Mouses over element.
     *
     * @param element Framework element.
     */
    @Override
    public void MouseOver(ParameterObject element) {

    }

    /**
     * Mouses out of element.
     *
     * @param element Framework element.
     */
    @Override
    public void MouseOutOf(ParameterObject element) {

    }

    /**
     * Simulates typing text into the element.
     *
     * @param element Framework element.
     */
    @Override
    public void SendKeysToElement(ParameterObject element) {

    }

    /**
     * Simulates typing text into the element.
     *
     * @param element Framework element.
     */
    @Override
    public void SendKeysToElement2(ParameterObject element) {

    }

    /**
     * Clicks the element.
     *
     * @param element Framework element.
     */
    @Override
    public void ClickElement(ParameterObject element) {

    }

    /**
     * Holds a click for the duration specified.
     *
     * @param element Framework element.
     */
    @Override
    public void ClickAndHoldElement(ParameterObject element) {

    }

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param element Framework element.
     */
    @Override
    public void ClickElementAtOffset(ParameterObject element) {

    }

    /**
     * Clicks the element using javascript.
     *
     * @param element Parameter Object.
     */
    @Override
    public void ClickByJavaScript(ParameterObject element) {

    }

    /**
     * Right clicks the element.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public void RightClickElement(ParameterObject element) {

    }

    /**
     * Double clicks the element.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public void DoubleClickElement(ParameterObject element) {

    }

    /**
     * Drags the element and drops it in the target.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public void DragAndDrop(ParameterObject element) {

    }

    /**
     * Scrolls to the top of the page.
     *
     * @param element Framework element.
     */
    @Override
    public void ScrollToTop(ParameterObject element) {

    }

    /**
     * Scrolls to the end of the page.
     *
     * @param element The ParameterObject element.
     */
    @Override
    public void ScrollToEnd(ParameterObject element) {

    }

    /**
     * Gets the value of the specified attribute for the element.
     *
     * @param element Framework element.
     * @return The attribute's current value. Returns a <see langword="null"/> if the value is not set.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public String GetElementAttributeOrText(ParameterObject element) {
        return null;
    }

    /**
     * Opens element of type input type=file.
     *
     * @param element Framework element.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public void OpenFileDialog(ParameterObject element) {

    }

    /**
     * Load a new web page in the current browser window.
     *
     * @param element Framework element.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="element"/> is <see langword="null"/>.
     */
    @Override
    public String GoToUrl(ParameterObject element) {
        return null;
    }

}