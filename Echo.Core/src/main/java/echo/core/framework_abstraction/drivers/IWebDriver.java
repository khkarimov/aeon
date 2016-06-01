package echo.core.framework_abstraction.drivers;

import com.sun.glass.ui.Size;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.controls.web.WebControl;

import java.net.URL;
import java.util.Collection;
import java.util.UUID;

/**
 * The framework adapter interface.
 */
public interface IWebDriver extends IDriver {
    WebControl FindElement(UUID guid, IBy selector);

    Collection<WebControl> FindElements(UUID guid, IBy selector);

    void Click(UUID guid, WebControl webControl);

    void DoubleClick(UUID guid, IBy selector);

    void AcceptAlert(UUID guid);

    void DismissAlert(UUID guid);

    void ScrollElementIntoView(UUID guid, WebControl webControl);

    void ScrollToTop(UUID guid);

    void ScrollToEnd(UUID guid);

    void SwitchToDefaultContent(UUID guid);

    void FocusWindow(UUID guid);

    void SwitchToFrame(UUID guid, IBy selector);

    String GetElementTagName(UUID guid, WebControl webControl);

    Object ExecuteScript(UUID guid, String script);

    void ClearElement(UUID guid, WebControl webControl);

    void AddCookie(UUID guid, IWebCookie cookie);

    void DeleteCookie(UUID guid, String cookie);

    void DeleteAllCookies(UUID guid);

    void GoBack(UUID guid);

    void GoForward(UUID guid);

    String GoToUrl(UUID guid, URL url);

    void Maximize(UUID guid);

    void Refresh(UUID guid);

    void ChooseSelectElementByValue(UUID guid, WebControl control, String value);

    void ChooseSelectElementByText(UUID guid, WebControl control, String value);

    void ClickElement(UUID guid, WebControl control);

    void SendKeysToElement(UUID guid, WebControl control, String value);

    String GetElementAttribute(UUID guid, WebControl control, String value);

    void SwitchToMainWindow(UUID guid);

    String SwitchToWindowByTitle(UUID guid, String title);

    void Resize(UUID guid, Size size);

    void Blur(UUID guid, WebControl control);
	
	void RightClick(UUID guid, WebControl element);

    void Check(UUID guid, WebControl element);

    void UnCheck(UUID guid, WebControl element);
	
	void ClickAndHold(UUID guid, WebControl element, int duration);

    void IsElementEnabled(UUID guid, WebControl element);

    void Exists(UUID guid, WebControl element);

    void NotExists(UUID guid, WebControl element);

    void OpenFileDialog(UUID guid, IBy selector);

    void VerifyAlertExists(UUID guid);

    void VerifyAlertNotExists(UUID guid);

    void SendKeysToAlert(UUID guid, String keysToSend);

    void DragAndDrop(UUID guid, IBy dropElement, IBy targetElement);

//
//    /**
//     * Check the element.
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