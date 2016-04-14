package echo.core.framework_interaction;

import echo.core.common.BrowserType;
import echo.core.common.ClientRects;
import echo.core.common.JQueryStringType;
import echo.core.common.Resources;
import echo.core.common.exceptions.NoSuchElementException;
import echo.core.common.exceptions.ScriptExecutionException;
import echo.core.common.parameters.ParameterObject;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.WebElement;
import echo.core.framework_abstraction.webdriver.ICookieAdapter;
import echo.core.framework_abstraction.webdriver.ISelectElementFactory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Web framework adapter.
 */
public class SeleniumFrameworkAdapter implements IFrameworkAdapter {
    private IDriver seleniumWebDriver;
    private UUID guid = UUID.randomUUID();
    private ISelectElementFactory selectElementFactory;

    /**
     * Initializes a new instance of the <see cref="SeleniumFrameworkAdapter"/> class.
     *
     * @param parameterObject      The parameterObject.
     * @param selectElementFactory The selectElementFactory.
     */
    public SeleniumFrameworkAdapter(ParameterObject parameterObject, ISelectElementFactory selectElementFactory) {
        seleniumWebDriver = parameterObject.getAutomationInfo().getDriverAdapter();
        guid = parameterObject.getGuid();
        this.selectElementFactory = selectElementFactory;
    }

    /**
     * Clicks on an element.
     *
     * @param parameterObject Framework param object from facade.
     */
    public final void Click(ParameterObject parameterObject) {
        seleniumWebDriver.Click(guid, parameterObject.getWeb().getWebElement());
    }

    /**
     * Checks an element.
     *
     * @param parameterObject The parameter object.
     * @return A bool that if true if the element was successfully checked.
     */
    public final boolean Check(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Unchecks an element.
     *
     * @param parameterObject The element to uncheck.
     * @return A bool that if true if the element was successfully unchecked.
     */
    public final boolean Uncheck(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Double clicks an element.
     *
     * @param parameterObject Parameter Object.
     */
    public final void DoubleClick(ParameterObject parameterObject) {
        if (parameterObject.getWeb().getFindIBy() == null) {
            throw new IllegalArgumentException("selector");
        }

        ScrollElementIntoView(parameterObject);
        seleniumWebDriver.DoubleClick(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
    }

    /**
     * Right clicks on an element.
     *
     * @param parameterObject Parameter Object.
     */
    public final void RightClick(ParameterObject parameterObject) {
        if (parameterObject.getWeb().getFindIBy() == null) {
            throw new IllegalArgumentException("selector");
        }

        ScrollElementIntoView(parameterObject);
        seleniumWebDriver.RightClick(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
    }

    /**
     * Clicks and holds on an element.
     *
     * @param parameterObject The parameter object.
     */
    public final void ClickAndHold(ParameterObject parameterObject) {
        seleniumWebDriver.ClickAndHold(parameterObject.getGuid(), parameterObject.getWeb().getWebElement(),
                parameterObject.getDuration());
    }

    /**
     * Control clicks on an element.
     *
     * @param parameterObject The parameter object.
     */
    public final void ControlClick(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Set a value.
     *
     * @param parameterObject The Parameter Object.
     */
    @Override
    public void Set(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Chooses a select web element by value.
     *
     * @param parameterObject Parameter Object.
     */
    public final void ChooseSelectElementByValue(ParameterObject parameterObject) {
        selectElementFactory.CreateInstance(parameterObject.getGuid(),
                parameterObject.getWeb().getWebElement().getWebElementAdapter(), parameterObject.getLog())
                .SelectByValue(parameterObject.getGuid(), parameterObject.getWeb().getValue());
    }

    /**
     * Chooses a select web element by text.
     *
     * @param parameterObject Parameter Object.
     */
    public final void ChooseSelectElementByText(ParameterObject parameterObject) {
        selectElementFactory.CreateInstance(parameterObject.getGuid(),
                parameterObject.getWeb().getWebElement().getWebElementAdapter(), parameterObject.getLog())
                .SelectByText(parameterObject.getGuid(), parameterObject.getWeb().getValue());
    }

    /**
     * Click by Javascript command.
     *
     * @param element Parameter Object.
     */
    public final void ClickByJavascript(ParameterObject element) {
        element.getWeb().setScript(element.getWeb().getFindIBy().ToJQuery().toString(JQueryStringType.ClickInvisibleElement));
        ExecuteScript(element);
    }

    /**
     * Set Value by Javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetValueByJavaScript(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets Div Value by Javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetDivValueByJavascript(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets Body Value by Javascript.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void SetBodyValueByJavascript(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Approximately Equal.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void ApproximatelyEqual(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Not visible.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void IsNotVisible(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param frameworkElement Framework element.
     * @return The mapped key.
     */
    public final String MapKeyboardKey(ParameterObject frameworkElement) {
        return Map(frameworkElement.getWeb().getKey());
    }

    /**
     * Maps a keyboard key to type <see cref="string"/>.
     *
     * @param key The key to map.
     * @return The mapped key.
     */
    public final String Map(Keys key) {
        return key.toString();
    }

    /**
     * Simulates typing text into the element.
     *
     * @param parameterObject Framework element.
     * @throws IllegalArgumentException If <paramref name="parameterObject"/> is <see langword="null"/>.
     */
    public final void SendKeysToElement(ParameterObject parameterObject) {
        parameterObject.getWeb().getWebElement().getWebElementAdapter()
                .SendKeys(guid, parameterObject.getWeb().getValue());
    }

    /**
     * Executes JavaScript in the context of the currently selected frame or window.
     *
     * @param frameworkElement Framework element.
     * @return The value returned by the script.
     * @throws ScriptExecutionException If the JavaScript encounters an error.
     */
    public final Object ExecuteScript(ParameterObject frameworkElement) {
        return seleniumWebDriver.ExecuteScript(
                guid,
                frameworkElement.getWeb().getScript(),
                frameworkElement.getWeb().getArgs());
    }

    /**
     * Checks if an element is empty.
     *
     * @param parameterObject The element to check.
     */
    public final void IsEmpty(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Clears the element.
     *
     * @param parameterObject The Parameter Object.
     */
    @Override
    public void Clear(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Mouses over the element.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void MouseOver(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Mouses out of the element.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void MouseOutOf(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the element tag name.
     *
     * @param parameterObject The Parameter Object.
     * @return The element tag name.
     */
    public final String GetElementTagName(ParameterObject parameterObject) {
        return parameterObject.getWeb().getWebElement().getWebElementAdapter().GetTagName(guid);
    }

    /**
     * Drags and drops the element to the destination.
     *
     * @param parameterObject The element to drag and drop.
     */
    public final void DragAndDrop(ParameterObject parameterObject) {
        if (parameterObject.getWeb().getDropElement() == null) {
            throw new IllegalArgumentException("dropElement");
        }

        if (parameterObject.getWeb().getDropTarget() == null) {
            throw new IllegalArgumentException("dropTarget");
        }

        seleniumWebDriver.DragAndDrop(parameterObject.getGuid(), parameterObject.getWeb().getDropElement(), parameterObject.getWeb().getDropTarget());
    }

    /**
     * Checks if the element is visible.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void IsVisible(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element exists.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void Exists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is does not exist.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void NotExists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * The blur method.
     *
     * @param parameterObject Parameter Object.
     */
    public final void Blur(ParameterObject parameterObject) {
        parameterObject.getWeb().setScript(parameterObject.getWeb().getFindIBy().ToJQuery().toString(JQueryStringType.BlurElement));
        try {
            ExecuteScript(parameterObject);
        } catch (ScriptExecutionException e) {
        }
    }

    /**
     * The sendkeys method.
     *
     * @param frameworkElement Parameter Object.
     */
    public final void SendKeys(ParameterObject frameworkElement) {
        frameworkElement.getWeb().getWebElement().getWebElementAdapter().SendKeys(guid, frameworkElement.getWeb().getKey());
    }

    /**
     * The Accept Alert method.
     *
     * @param parameterObject Parameter Object.
     */
    public final void AcceptAlert(ParameterObject parameterObject) {
        seleniumWebDriver.AcceptAlert(parameterObject.getGuid());
    }

    /**
     * The Dismiss Alert Method.
     *
     * @param parameterObject Parameter Object.
     */
    public final void DismissAlert(ParameterObject parameterObject) {
        seleniumWebDriver.DismissAlert(parameterObject.getGuid());
    }

    /**
     * The Verify Alert Method.
     *
     * @param parameterObject Parameter Object.
     */
    public final void VerifyAlertExists(ParameterObject parameterObject) {
        seleniumWebDriver.VerifyAlertExists(parameterObject.getGuid());
    }

    /**
     * The Verify Alert Not Exists Command.
     *
     * @param parameterObject Parameter Object.
     */
    public final void VerifyAlertNotExists(ParameterObject parameterObject) {
        seleniumWebDriver.VerifyAlertNotExists(parameterObject.getGuid());
    }

    /**
     * The get alert text command.
     *
     * @param parameterObject Parameter Object.
     * @return Alert text.
     */
    public final String GetAlertText(ParameterObject parameterObject) {
        return seleniumWebDriver.GetAlertText(parameterObject.getGuid());
    }

    /**
     * The send keys to alert command.
     *
     * @param parameterObject Parameter Object.
     */
    public final void SendKeysToAlert(ParameterObject parameterObject) {
        seleniumWebDriver.SendKeysToAlert(parameterObject.getGuid(), parameterObject.getWeb().getKeysToSend());
    }

    /**
     * Asserts a control exisits.
     *
     * @param parameterObject The parameter object.
     * @return A bool that is true if the control exists and fale if it doesn't.
     */
    public final boolean ControlExists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if an element exists but is not visible.
     *
     * @param parameterObject The parameter object.
     */
    public final void NotVisibleExists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is enabled.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void Enabled(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is not enabled.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void NotEnabled(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is selected.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void Selected(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is not selected.
     *
     * @param parameterObject The element to check.
     */
    @Override
    public void NotSelected(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if an element has an option.
     *
     * @param parameterObject The parameter object.
     */
    public final void HasOption(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has a certain number of options.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasNumberOfOptions(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has a set of options.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasOptions(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has a set of options in order.
     *
     * @param parameterObject The options to check for.
     */
    @Override
    public void HasOptionsInOrder(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element does not have a certain option.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void DoesNotHaveOption(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element does not have a set of options.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void DoesNotHaveOptions(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has the messages.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void Has(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element does not have the messages.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void DoesNotHave(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has the messages.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasPartial(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element does not have the messages.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void DoesNotHavePartial(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element only has the messages.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasOnly(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element has comparable options.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void HasComparableOptions(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if something is a certain value.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void Is(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is like a certain value.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void Like(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is not like a certain value.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void NotLike(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current context
     * using the given mechanism.
     *
     * @param frameworkElement Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="frameworkElement"/> is <see langword="null"/>.
     */
    public final List<WebElement> FindElements(ParameterObject frameworkElement) {
        return new ArrayList<>(
                frameworkElement.getWeb().getWebElement().getWebElementAdapter()
                        .FindElements(guid, frameworkElement.getWeb().getFindIBy())
                        .stream().map(x -> new WebElement(x, frameworkElement.getWeb().getFindIBy())).collect(Collectors.toList()));
    }

    /**
     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     *
     * @param frameworkElement Framework element.
     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
     */
    public final String GetElementText(ParameterObject frameworkElement) {
        return frameworkElement.getWeb().getWebElement().getWebElementAdapter().GetText(guid);
    }

    /**
     * Finds all web elements within the current context
     * using the given mechanism.
     *
     * @param frameworkElement Framework element.
     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see>
     * matching the current criteria, or an empty list if nothing matches.
     * @throws IllegalArgumentException If <paramref name="frameworkElement"/> is <see langword="null"/>.
     * @throws NoSuchElementException   If there is no such element.
     */
    public final List<WebElement> FindIElements(ParameterObject frameworkElement) {
        return new ArrayList<WebElement>(
                seleniumWebDriver.FindElements(guid, frameworkElement.getWeb().getFindIBy())
                        .stream().map(x -> new WebElement(x, frameworkElement.getWeb().getFindIBy()))
                        .collect(Collectors.toList()));
    }

    /**
     * Captures and image.
     *
     * @param parameterObject The element in question.
     */
    public final void CaptureImage(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The current scroll position.
     */
    public final int GetScrollPosition(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the scroll position.
     *
     * @param parameterObject Parameter Object.
     */
    public final void SetScrollPosition(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the maximum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The maximum scroll position.
     */
    public final int GetMaximumScrollPosition(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the minimum scroll position.
     *
     * @param parameterObject The parameter object.
     * @return The minimum scroll position.
     */
    public final int GetMinimumScrollPosition(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Waits until the control element exists.
     *
     * @param parameterObject The parameter object.
     */
    public final void WaitForControlExist(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Waits until the parent control contains a certain number of occurances of the child element.
     *
     * @param parameterObject The parameter object.
     */
    public final void WaitForControlContains(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Closes window.
     *
     * @param parameterObject The parameter object.
     */
    public final void Close(ParameterObject parameterObject) {
        seleniumWebDriver.Close(guid);
    }

    /**
     * Quits thing thing.
     *
     * @param parameterObject The parameter object.
     */
    public final void Quit(ParameterObject parameterObject) {
        seleniumWebDriver.Quit(guid);
    }

    /**
     * Adds a cookie.
     *
     * @param parameterObject The parameter object.
     */
    public final void AddCookie(ParameterObject parameterObject) {
        if (parameterObject.getWeb().getCookie() == null) {
            throw new IllegalArgumentException("cookie");
        }

        seleniumWebDriver.AddCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookie());
    }

    /**
     * Deletes all cookies.
     *
     * @param parameterObject The parameter object.
     */
    public final void DeleteAllCookies(ParameterObject parameterObject) {
        seleniumWebDriver.DeleteAllCookies(parameterObject.getGuid());
    }

    /**
     * Deletes a cookie.
     *
     * @param parameterObject The parameter object.
     */
    public final void DeleteCookie(ParameterObject parameterObject) {
        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
            throw new IllegalArgumentException("cookieName");
        }

        seleniumWebDriver.DeleteCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookieName());
    }

    /**
     * Gets all cookies.
     *
     * @param parameterObject The parameter object.
     * @return List of cookies.
     */
    public final List<ICookieAdapter> GetAllCookies(ParameterObject parameterObject) {
        return seleniumWebDriver.GetAllCookies(guid);
    }

    /**
     * Gets a cookie.
     *
     * @param parameterObject The parameter object.
     * @return The cookie adapter.
     */
    public final ICookieAdapter GetCookie(ParameterObject parameterObject) {
        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
            throw new IllegalArgumentException("cookieName");
        }

        return seleniumWebDriver.GetCookie(guid, parameterObject.getWeb().getCookieName());
    }

    /**
     * Modifies a cookie.
     *
     * @param parameterObject The parameter object.
     */
    public final void ModifyCookie(ParameterObject parameterObject) {
        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
            throw new IllegalArgumentException("cookieName");
        }

        seleniumWebDriver.ModifyCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookieName(), parameterObject.getWeb().getValue());
    }

    /**
     * Verify the title.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void VerifyTitle(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Verify the url.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void VerifyUrl(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Scroll to top of page.
     *
     * @param parameterObject The parameter object.
     */
    public final void ScrollToTop(ParameterObject parameterObject) {
        seleniumWebDriver.ScrollToTop(parameterObject.getGuid());
    }

    /**
     * Scroll to the end of the page.
     *
     * @param parameterObject The parameter object.
     */
    public final void ScrollToEnd(ParameterObject parameterObject) {
        seleniumWebDriver.ScrollToEnd(parameterObject.getGuid());
    }

    /**
     * Clears browser storage.
     *
     * @param parameterObject The paramter object.
     */
    public final void ClearBrowserStorage(ParameterObject parameterObject) {
        ExecuteScript(parameterObject);
    }

    /**
     * Switches to a window by the title.
     *
     * @param parameterObject The paramter object.
     * @return The current handler after the change.
     */
    public final String SwitchToWindowByTitle(ParameterObject parameterObject) {
        if (parameterObject.getWeb().getWindowTitle() == null) {
            throw new IllegalArgumentException("windowTitle");
        }

        if (parameterObject.getWeb().getWindowTitle().length() == 0) {
            throw new IllegalArgumentException(Resources.getString("Argument_StringZeroLength") + " = windowTitle");
        }

        return seleniumWebDriver.SwitchToWindowByTitle(parameterObject.getGuid(), parameterObject.getWeb().getWindowTitle());
    }

    /**
     * Switches to the main window.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void SwitchToMainWindow(ParameterObject parameterObject) {

    }

    /**
     * Switches to a window by the url.
     *
     * @param parameterObject The paramter object.
     * @return The current handler after the change.
     */
    public final String SwitchToWindowByUrl(ParameterObject parameterObject) {
        return seleniumWebDriver.SwitchToWindowByUrl(parameterObject.getGuid(), parameterObject.getWeb().getUrlString());
    }

    /**
     * Checks if a window does not exist by the title.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void WindowDoesNotExistByTitle(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if a window does not exist by the url.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void WindowDoesNotExistByUrl(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Maximizes the window.
     *
     * @param parameterObject The paramter object.
     */
    public final void Maximize(ParameterObject parameterObject) {
        seleniumWebDriver.Maximize(parameterObject.getGuid());
    }

    /**
     * Resizes the window.
     *
     * @param parameterObject The paramter object.
     */
    public final void Resize(ParameterObject parameterObject) {
        seleniumWebDriver.Resize(parameterObject.getGuid(), parameterObject.getWeb().getSize());
    }

    /**
     * Goes back.
     *
     * @param parameterObject The paramter object.
     */
    public final void GoBack(ParameterObject parameterObject) {
        seleniumWebDriver.Back(parameterObject.getGuid());
    }

    /**
     * Goes forward.
     *
     * @param parameterObject The paramter object.
     */
    public final void GoForward(ParameterObject parameterObject) {
        seleniumWebDriver.Forward(parameterObject.getGuid());
    }

    /**
     * Goes to a Url.
     *
     * @param parameterObject The paramter object.
     * @return The current handler after the change.
     */
    public final String GoToUrl(ParameterObject parameterObject) {
        return seleniumWebDriver.GoToUrl(parameterObject.getGuid(), parameterObject.getWeb().getUrl());
    }

    /**
     * Refreshs the page.
     *
     * @param parameterObject The paramter object.
     */
    public final void Refresh(ParameterObject parameterObject) {
        seleniumWebDriver.Refresh(parameterObject.getGuid());
    }

    /**
     * Appends a query string.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void AppendQueryString(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Grid Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void GridNotExists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    /**
     * Row Not Exists.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void RowNotExists(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Refresh frame.
     *
     * @param parameterObject The paramter object.
     */
    public final void RefreshFrame(ParameterObject parameterObject) {
        seleniumWebDriver.RefreshFrame(parameterObject.getGuid());
    }

    /**
     * Gets the browser type.
     *
     * @param parameterObject The paramter object.
     * @return The browser type.
     */
    public final BrowserType GetBrowserType(ParameterObject parameterObject) {
        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.appName; return browserType;");
        String name = ExecuteScript(parameterObject).toString();
        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.appVersion; return browserType;");
        String version = ExecuteScript(parameterObject).toString();
        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.userAgent; return browserType;");
        String userAgent = ExecuteScript(parameterObject).toString();

        if (name.toLowerCase().contains("internet") && name.toLowerCase().contains("explorer")) {
            return BrowserType.InternetExplorer;
        } else if (version.toLowerCase().contains("chrome")) {
            return BrowserType.Chrome;
        } else if (userAgent.contains("Firefox")) {
            return BrowserType.Firefox;
        }

        throw new RuntimeException("Browser type not recognized");
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
     * Gets the element attribute.
     *
     * @param parameterObject The paramter object.
     * @return The element attribute.
     */
    @Override
    public String GetElementAttribute(ParameterObject parameterObject) {
        return null;
    }

    /**
     * Edits the menu navigation scroll.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void EditMenuNavigationScroll(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Fires change event.
     *
     * @param parameterObject The parameter object.
     */
    @Override
    public void FireChangeEvent(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * JQuery before or after.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void JQueryBeforeOrAfter(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Open file dialog.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void OpenFileDialog(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Select file dialog.
     *
     * @param parameterObject The paramter object.
     */
    @Override
    public void SelectFileDialog(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Scrolls the element into view.
     *
     * @param parameterObject The element to scroll.
     */
    @Override
    public void ScrollElementIntoView(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is not read only.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void NotReadOnly(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if the element is read only.
     *
     * @param parameterObject Parameter Object.
     */
    @Override
    public void ReadOnly(ParameterObject parameterObject) {
        throw new UnsupportedOperationException();
    }
}
