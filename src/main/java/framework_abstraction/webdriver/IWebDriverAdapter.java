package framework_abstraction.webdriver;

import com.sun.glass.ui.Size;
import com.sun.prism.Image;
import common.exceptions.*;
import common.webobjects.interfaces.IBy;
import framework_abstraction.WebElement;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Defines the interface through which the user controls the browser.
 */
public interface IWebDriverAdapter {
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
    URI GetUrl(UUID guid);

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
     * @throws IllegalArgumentException If <paramref name="url"/> is <see langword="null"/>.
     */
    String GoToUrl(UUID guid, URI url);

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
     * Finds the first <see cref="IWebElementAdapter"/> using the given method.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebElementAdapter"/> on the current context.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    IWebElementAdapter FindElement(UUID guid, IBy findBy);

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see>
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    Collection<IWebElementAdapter> FindElements(UUID guid, IBy findBy);

    /**
     * Finds the first <see cref="IWebSelectElementAdapter"/> using the given method.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param findBy The locating mechanism to use.
     * @return The first matching <see cref="IWebSelectElementAdapter"/> on the current context.
     * @throws IllegalArgumentException                 If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws NoSuchElementException                   If there is no such element.
     * @throws ScriptReturnValueNotHtmlElementException If the JavaScript did not return an HTML element.
     * @throws UnsupportedSelectElementException        If the select element is not supported.
     */
    IWebSelectElementAdapter FindSelectElement(UUID guid, IBy findBy);

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
     * @throws IllegalArgumentException    If <paramref name="findBy"/> is <see langword="null"/>.
     * @throws UnsupportedElementException If the element is not supported.
     */
    void SwitchToFrame(UUID guid, IBy findBy);

    /**
     * Switches the focus of future commands for this driver to the window with the given title.
     *
     * @param guid        A globally unique identifier associated with this call.
     * @param windowTitle The title of the window to select.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="windowTitle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    String SwitchToWindowByTitle(UUID guid, String windowTitle);

    /**
     * Switches the focus of future commands for this driver to the window with the given handle.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param handle The handle of the window to select.
     * @throws IllegalArgumentException If <paramref name="handle"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="handle"/> is empty.
     * @throws NoSuchWindowException    If the window cannot be found.
     */
    void SwitchToWindowByHandle(UUID guid, String handle);

    /**
     * Switches the focus of future commands for this driver to the window with the given url.
     *
     * @param guid  A globally unique identifier associated with this call.
     * @param value The URL to which to switch focus.
     * @return The current handler after the change.
     * @throws IllegalArgumentException If <paramref name="value"/> is <see langword="null"/>.
     * @throws IllegalArgumentException If <paramref name="value"/> is empty.
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
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param script The JavaScript code to execute.
     * @param args   The arguments to the script.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    Object ExecuteScript(UUID guid, String script, Object[]... args);

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
     * Scrolls to the element on the page if in Chrome.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param selector The selector.
     */
    void ScrollElementIntoView(UUID guid, IBy selector);

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
     * @param selector The element to right click.
     */
    void RightClick(UUID guid, IBy selector);

    /**
     * Clicks an element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to click.
     */
    void Click(UUID guid, WebElement element);

    /**
     * Double clicks an element.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to double click.
     */
    void DoubleClick(UUID guid, IBy element);

    /**
     * Holds a click on an element for the duration specified.
     *
     * @param guid     A globally unique identifier associated with this call.
     * @param element  The web element to click.
     * @param duration Click for at least this long (in milliseconds).
     */
    void ClickAndHold(UUID guid, WebElement element, int duration);

    /**
     * Clicks the element at the specified (x, y) offset.
     *
     * @param guid    A globally unique identifier associated with this call.
     * @param element The web element to click.
     * @param x       The x offset.
     * @param y       The y offset.
     */
    void ClickAtOffset(UUID guid, WebElement element, int x, int y);

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
    ArrayList<ICookieAdapter> GetAllCookies(UUID guid);

    /**
     * Gets a cookie.
     *
     * @param guid A globally unique identifier associated with this call.
     * @param name Name of the cookie to retrieve.
     * @return The specified cookie.
     */
    ICookieAdapter GetCookie(UUID guid, String name);

    /**
     * Adds a cookie.
     *
     * @param guid   A globally unique identifier associated with this call.
     * @param cookie The cookie to be added.
     */
    void AddCookie(UUID guid, ICookieAdapter cookie);

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
    IWebElementAdapter GetFocusedElement(UUID guid);
}