package echo.core.framework_abstraction.drivers;

import com.sun.glass.ui.Size;
import echo.core.common.CompareType;
import echo.core.common.ComparisonOption;
import echo.core.common.KeyboardKey;
import echo.core.common.web.BrowserType;
import echo.core.common.web.ClientRects;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.adapters.IAdapter;
import echo.core.framework_abstraction.adapters.IWebAdapter;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import echo.core.framework_abstraction.controls.web.WebControl;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.UUID;

/**
 * Web framework adapter.
 */
public class EchoWebDriver implements IWebDriver {
    private IWebAdapter adapter;

    /**
     * Initializes a new instance of the EchoWebDriver class.
     */
    public EchoWebDriver() {
    }

    @Override
    public IDriver Configure(IAdapter adapter) {
        this.adapter = (IWebAdapter)adapter;
        return this;
    }

    @Override
    public WebControl FindElement(UUID guid, IBy selector) {
        return adapter.FindElement(guid, selector);
    }

    @Override
    public Collection<WebControl> FindElements(UUID guid, IBy selector) {
        return adapter.FindElements(guid, selector);
    }

    @Override
    public void Click(UUID guid, WebControl webControl) {
        adapter.Click(guid, webControl);
    }

    @Override
    public void DoubleClick(UUID guid, IBy selector) {adapter.DoubleClick(guid, selector);}

    @Override
    public void ScrollElementIntoView(UUID guid, WebControl control) {
        adapter.ScrollElementIntoView(guid, control);
    }

    @Override
    public void ScrollToTop(UUID guid){
        adapter.ScrollToTop(guid);
    }

    @Override
    public void ScrollToEnd(UUID guid){
        adapter.ScrollToEnd(guid);
    }

    @Override
    public void SwitchToDefaultContent(UUID guid) {
        adapter.SwitchToDefaultContent(guid);
    }

    @Override
    public void FocusWindow(UUID guid) {
        adapter.FocusWindow(guid);
    }

    @Override
    public void SwitchToFrame(UUID guid, IBy selector) {
        adapter.SwitchToFrame(guid, selector);
    }

    @Override
    public String GetElementTagName(UUID guid, WebControl element) {
        return adapter.GetElementTagName(guid, element);
    }

    @Override
    public Object ExecuteScript(UUID guid, String script) {
        return adapter.ExecuteScript(guid, script);
    }

    @Override
    public void ClearElement(UUID guid, WebControl element) {
        adapter.ClearElement(guid, element);
    }

    @Override
    public void AddCookie(UUID guid, IWebCookie cookie) {
        adapter.AddCookie(guid, cookie);
    }

    @Override
    public void DeleteCookie(UUID guid, String cookie) { adapter.DeleteCookie(guid, cookie);}

    @Override
    public void DeleteAllCookies(UUID guid) {adapter.DeleteAllCookies(guid);}

    @Override
    public void GoBack(UUID guid) {
        adapter.Back(guid);
    }

    @Override
    public void GoForward(UUID guid) { adapter.Forward(guid); }

    @Override
    public String GoToUrl(UUID guid, URL url) {
        return adapter.GoToUrl(guid, url);
    }

    @Override
    public void Maximize(UUID guid) {
        adapter.Maximize(guid);
    }

    @Override
    public void Refresh(UUID guid) {
        adapter.Refresh(guid);
    }

    @Override
    public void ChooseSelectElementByValue(UUID guid, WebControl element, String value) {
        adapter.ChooseSelectElementByValue(guid, element, value);
    }

    @Override
    public void ChooseSelectElementByText(UUID guid, WebControl element, String value) {
        adapter.ChooseSelectElementByText(guid, element, value);
    }

    @Override
    public void ClickElement(UUID guid, WebControl element) {
        adapter.ClickElement(guid, element);
    }

    @Override
    public void SendKeysToElement(UUID guid, WebControl element, String value) {
        adapter.SendKeysToElement(guid, element, value);
    }

    @Override
    public String GetElementAttribute(UUID guid, WebControl element, String value) {
        return adapter.GetElementAttribute(guid, element, value);
    }

    @Override
    public void SwitchToMainWindow(UUID guid) {
        adapter.SwitchToMainWindow(guid);
    }

    @Override
    public String SwitchToWindowByTitle(UUID guid, String title){
        return adapter.SwitchToWindowByTitle(guid, title);
    }

    @Override
    public void Resize(UUID guid, Size size) {
        adapter.Resize(guid, size);
    }

    @Override
    public Image GetScreenshot() {
        return adapter.GetScreenshot(UUID.randomUUID());
    }

    @Override
    public String GetSource() {
        return adapter.GetPageSource(UUID.randomUUID());
    }

    @Override
    public void Close(UUID guid) {
        adapter.Close(guid);
    }

    @Override
    public void Quit(UUID guid) {
        adapter.Quit(guid);
    }

    @Override
    public void AcceptAlert(UUID guid) {adapter.AcceptAlert(guid);}

    @Override
    public void DismissAlert(UUID guid) {adapter.DismissAlert(guid);}

    @Override
    public String GetAlertText(UUID guid) {return adapter.GetAlertText(guid);}

    @Override
    public void Blur(UUID guid, WebControl element) {adapter.Blur(guid, element);}

    @Override
    public void RightClick(UUID guid, WebControl element) {adapter.RightClick(guid, element.getSelector());}

    @Override
	public void Check(UUID guid, WebControl element) {adapter.CheckElement(guid, element);}

    @Override
    public void UnCheck(UUID guid, WebControl element) {adapter.UnCheckElement(guid, element);}

	@Override
	public void ClickAndHold(UUID guid, WebControl element, int duration) {adapter.ClickAndHold(guid, element, duration);}

    @Override
    public void IsElementEnabled(UUID guid, WebControl element) {adapter.IsElementEnabled(guid, element);}

    @Override
    public void Exists(UUID guid, WebControl element) {adapter.Exists(guid, element);}

    @Override
    public void NotExists(UUID guid, WebControl element) {adapter.NotExists(guid, element);}

    @Override
    public void HasOptions(UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {adapter.ElementHasOptions(guid, element, options, optgroup, select);}

    @Override
    public void DoesNotHaveOptions(UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {adapter.ElementDoesNotHaveOptions(guid, element, options, optgroup, select);}

    @Override
    public void OpenFileDialog(UUID guid, IBy selector){
        adapter.OpenFileDialog(guid, selector);
    }

    @Override
    public void SelectFileDialog(UUID guid, IBy selector, String path){
        adapter.SelectFileDialog(guid, selector, path);
    }

    @Override
    public void UploadFileDialog(UUID guid, IBy selector, String path){
        adapter.UploadFileDialog(guid, selector, path);
    }

    @Override
    public void VerifyAlertExists(UUID guid) {
        adapter.VerifyAlertExists(guid);
    }

    @Override
    public void VerifyAlertNotExists(UUID guid) {
        adapter.VerifyAlertNotExists(guid);
    }

    @Override
    public void SendKeysToAlert(UUID guid, String keysToSend) {
        adapter.SendKeysToAlert(guid, keysToSend);
    }

    @Override
    public void DragAndDrop(UUID guid, IBy dropElement, IBy targetElement) {
        adapter.DragAndDrop(guid, dropElement, targetElement);
    }

    @Override
    public void ClickAllElements(UUID guid, IBy elementsBy) {
        adapter.ClickAllElements(guid, elementsBy);
    }

    @Override
    public void MouseOut(UUID guid,WebControl element) { adapter.MouseOut(guid, element); }

    @Override
    public void MouseOver(UUID guid, WebControl element) { adapter.MouseOver(guid, element); }

    @Override
    public void SetBodyValueByJavaScript(UUID guid,WebControl element, String value) { adapter.SetBodyValueByJavaScript(guid, element, value); }

    @Override
    public void SetValueByJavaScript(UUID guid, WebControl element, String value) { adapter.SetValueByJavaScript(guid, element, value); }

    @Override
    public void SetDivValueByJavaScript(UUID guid, WebControl element, String value) { adapter.SetDivValueByJavaScript(guid, element, value); }

    @Override
    public void HasOptionsInOrder(UUID guid, WebControl element, String [] options, String optgroup, WebSelectOption select) {
        adapter.ElementHasOptionsInOrder(guid, element, options, optgroup, select);
    }

    @Override
    public void HasNumberOfOptions(UUID guid, WebControl element, int optnumber, String optgroup) {
        adapter.HasNumberOfOptions(guid, element, optnumber, optgroup);
    }

    @Override
    public void HasAllOptionsInOrder(UUID guid, WebControl element, CompareType compare, String optGroup) {
        adapter.HasAllOptionsInOrder(guid, element, compare, optGroup);
    }

    @Override
    public Collection <IWebCookie> GetAllCookies(UUID guid) {
        return adapter.GetAllCookies(guid);
    }

    @Override
    public void ModifyCookie (UUID guid, String name, String value) {
        adapter.ModifyCookie(guid, name, value);
    }

    @Override
    public IWebCookie GetCookie(UUID guid, String name) {
        return adapter.GetCookie(guid, name);
    }

    @Override
    public void Has(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        adapter.Has(guid, element, messages, selector, option, attribute);
    }

    @Override
    public void HasLike(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        adapter.HasLike(guid, element, messages, selector, option, attribute);
    }

    @Override
    public void DoesNotHave (UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        adapter.DoesNotHave(guid, element, messages, selector, option, attribute);
    }

    @Override
    public void DoesNotHaveLike (UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        adapter.DoesNotHaveLike(guid, element, messages, selector, option, attribute);
    }

    @Override
    public void HasOnly(UUID guid, WebControl element, String [] messages, String selector, ComparisonOption option, String attribute) {
        adapter.HasOnly(guid, element, messages, selector,  option, attribute);
    }

    @Override
    public void Is(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.Is(guid, element, value, option, attribute);
    }

    @Override
    public void IsLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.IsLike(guid, element, value, option, attribute);
    }

    @Override
    public void IsElementDisabled(UUID guid, WebControl element) {
        adapter.IsElementDisabled(guid, element);
    }

    @Override
    public void VerifyAlertText(UUID guid, String comparingText) {
        adapter.VerifyAlertText(guid, comparingText);
    }

    @Override
    public void VerifyAlertTextLike(UUID guid, String comparingText, boolean caseSensitive) {
        adapter.VerifyAlertTextLike(guid, comparingText, caseSensitive);
    }

    @Override
    public void VerifyTitle(UUID guid, String comparingTitle) {
        adapter.VerifyTitle(guid, comparingTitle);
    }

    @Override
    public void VerifyURL(UUID guid, URL comparingURL) {
        adapter.VerifyURL(guid, comparingURL);
    }

    @Override
    public void DatesApproximatelyEqual(UUID guid, WebControl element, String attributeName, DateTime expected, Period delta) {
        adapter.DatesApproximatelyEqual(guid, element, attributeName, expected, delta);
    }

    @Override
    public BrowserType GetBrowserType(UUID guid) {
        return adapter.GetBrowserType(guid);
    }

    @Override
    public void IsNotLike(UUID guid, WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.IsNotLike(guid, element, value, option, attribute);
    }

    @Override
    public ClientRects GetClientRects(UUID guid, WebControl element) {
        return adapter.GetClientRects(guid, element);
    }

    @Override
    public void PressKeyboardKey(UUID guid, WebControl element, KeyboardKey key) {adapter.PressKeyboardKey(guid, element, key);}
//
//    /**
//     * Checks an element.
//     *
//     * @param parameterObject The parameter object.
//     * @return A bool that if true if the element was successfully checked.
//     */
//    public final boolean Check(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Unchecks an element.
//     *
//     * @param parameterObject The element to uncheck.
//     * @return A bool that if true if the element was successfully unchecked.
//     */
//    public final boolean Uncheck(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Double clicks an element.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void DoubleClick(ParameterObject parameterObject) {
//        if (parameterObject.getWeb().getFindIBy() == null) {
//            throw new IllegalArgumentException("selector");
//        }
//
//        ScrollElementIntoView(parameterObject);
//        adapter.DoubleClick(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
//    }
//
//    /**
//     * Right clicks on an element.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void RightClick(ParameterObject parameterObject) {
//        if (parameterObject.getWeb().getFindIBy() == null) {
//            throw new IllegalArgumentException("selector");
//        }
//
//        ScrollElementIntoView(parameterObject);
//        adapter.RightClick(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
//    }
//
//    /**
//     * Clicks and holds on an element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void ClickAndHold(ParameterObject parameterObject) {
//        adapter.ClickAndHold(parameterObject.getGuid(), parameterObject.getWeb().getWebElement(),
//                parameterObject.getDuration());
//    }
//
//    /**
//     * Control clicks on an element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void ControlClick(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Set a value.
//     *
//     * @param parameterObject The Parameter Object.
//     */
//    @Override
//    public void Set(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Chooses a select web element by value.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void ChooseSelectElementByValue(ParameterObject parameterObject) {
//        parameterObject.getWeb().getWebElement().SelectByValue(parameterObject.getGuid(), parameterObject.getWeb().getValue());
//    }
//
//    /**
//     * Chooses a select web element by text.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void ChooseSelectElementByText(ParameterObject parameterObject) {
//        //TODO: WTF PARAMETER OBJECT getWeb()?
//        parameterObject.getWeb().getWebElement().SelectByText(parameterObject.getGuid(), parameterObject.getWeb().getValue());
//    }
//
//    /**
//     * Click by Javascript command.
//     *
//     * @param element Parameter Object.
//     */
//    public final void ClickByJavascript(ParameterObject element) {
//        element.getWeb().setScript(element.getWeb().getFindIBy().ToJQuery().toString(JQueryStringType.ClickInvisibleElement));
//        ExecuteScript(element);
//    }
//
//    /**
//     * Set Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void SetValueByJavaScript(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Sets Div Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void SetDivValueByJavascript(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Sets Body Value by Javascript.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void SetBodyValueByJavascript(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Approximately Equal.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void ApproximatelyEqual(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Not visible.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void IsNotVisible(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Maps a keyboard key to type <see cref="string"/>.
//     *
//     * @param frameworkElement Framework element.
//     * @return The mapped key.
//     */
//    public final String MapKeyboardKey(ParameterObject frameworkElement) {
//        return Map(frameworkElement.getWeb().getKey());
//    }
//
//    /**
//     * Maps a keyboard key to type <see cref="string"/>.
//     *
//     * @param key The key to map.
//     * @return The mapped key.
//     */
//    public final String Map(Keys key) {
//        return key.toString();
//    }
//
//    /**
//     * Simulates typing text into the element.
//     *
//     * @param parameterObject Framework element.
//     * @throws IllegalArgumentException If <paramref name="parameterObject"/> is <see langword="null"/>.
//     */
//    public final void SendKeysToElement(ParameterObject parameterObject) {
//        parameterObject.getWeb().getWebElement().SendKeys(parameterObject.getGuid(), parameterObject.getWeb().getValue());
//    }
//
//    /**
//     * Executes JavaScript in the product of the currently selected frame or window.
//     *
//     * @param parameterObject Framework element.
//     * @return The value returned by the script.
//     * @throws ScriptExecutionException If the JavaScript encounters an error.
//     */
//    public final Object ExecuteScript(ParameterObject parameterObject) {
//        return adapter.ExecuteScript(
//                parameterObject.getGuid(),
//                parameterObject.getWeb().getScript(),
//                parameterObject.getWeb().getArgs());
//    }
//
//    /**
//     * Checks if an element is empty.
//     *
//     * @param parameterObject The element to check.
//     */
//    public final void IsEmpty(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Clears the element.
//     *
//     * @param parameterObject The Parameter Object.
//     */
//    @Override
//    public void Clear(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Mouses over the element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void MouseOver(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Mouses out of the element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void MouseOutOf(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Gets the element tag name.
//     *
//     * @param parameterObject The Parameter Object.
//     * @return The element tag name.
//     */
//    public final String GetElementTagName(ParameterObject parameterObject) {
//        return parameterObject.getWeb().getWebElement().GetTagName(parameterObject.getGuid());
//    }
//
//    /**
//     * Drags and drops the element to the destination.
//     *
//     * @param parameterObject The element to drag and drop.
//     */
//    public final void DragAndDrop(ParameterObject parameterObject) {
//        if (parameterObject.getWeb().getDropElement() == null) {
//            throw new IllegalArgumentException("dropElement");
//        }
//
//        if (parameterObject.getWeb().getDropTarget() == null) {
//            throw new IllegalArgumentException("dropTarget");
//        }
//
//        adapter.DragAndDrop(parameterObject.getGuid(), parameterObject.getWeb().getDropElement(), parameterObject.getWeb().getDropTarget());
//    }
//
//    /**
//     * Checks if the element is visible.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void IsVisible(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element exists.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void Exists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is does not exist.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void NotExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * The blur method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void Blur(ParameterObject parameterObject) {
//        parameterObject.getWeb().setScript(parameterObject.getWeb().getFindIBy().ToJQuery().toString(JQueryStringType.BlurElement));
//        try {
//            ExecuteScript(parameterObject);
//        } catch (ScriptExecutionException e) {
//        }
//    }
//
//    /**
//     * The sendkeys method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void SendKeys(ParameterObject parameterObject) {
//        parameterObject.getWeb().getWebElement().SendKeys(parameterObject.getGuid(), parameterObject.getWeb().getKey());
//    }
//
//    /**
//     * The Accept Alert method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void AcceptAlert(ParameterObject parameterObject) {
//        adapter.AcceptAlert(parameterObject.getGuid());
//    }
//
//    /**
//     * The Dismiss Alert Method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void DismissAlert(ParameterObject parameterObject) {
//        adapter.DismissAlert(parameterObject.getGuid());
//    }
//
//    /**
//     * The Verify Alert Method.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void VerifyAlertExists(ParameterObject parameterObject) {
//        adapter.VerifyAlertExists(parameterObject.getGuid());
//    }
//
//    /**
//     * The Verify Alert Not Exists Command.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void VerifyAlertNotExists(ParameterObject parameterObject) {
//        adapter.VerifyAlertNotExists(parameterObject.getGuid());
//    }
//
//    /**
//     * The get alert text command.
//     *
//     * @param parameterObject Parameter Object.
//     * @return Alert text.
//     */
//    public final String GetAlertText(ParameterObject parameterObject) {
//        return adapter.GetAlertText(parameterObject.getGuid());
//    }
//
//    /**
//     * The send keys to alert command.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void SendKeysToAlert(ParameterObject parameterObject) {
//        adapter.SendKeysToAlert(parameterObject.getGuid(), parameterObject.getWeb().getKeysToSend());
//    }
//
//    /**
//     * Asserts a control exisits.
//     *
//     * @param parameterObject The parameter object.
//     * @return A bool that is true if the control exists and fale if it doesn't.
//     */
//    public final boolean ControlExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if an element exists but is not visible.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void NotVisibleExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is enabled.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void Enabled(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is not enabled.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void NotEnabled(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is selected.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void Selected(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is not selected.
//     *
//     * @param parameterObject The element to check.
//     */
//    @Override
//    public void NotSelected(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if an element has an option.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void HasOption(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has a certain number of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void HasNumberOfOptions(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has a set of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void HasOptions(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has a set of options in order.
//     *
//     * @param parameterObject The options to check for.
//     */
//    @Override
//    public void HasOptionsInOrder(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element does not have a certain option.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void DoesNotHaveOption(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element does not have a set of options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void DoesNotHaveOptions(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void Has(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element does not have the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void DoesNotHave(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void HasPartial(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element does not have the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void DoesNotHavePartial(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element only has the messages.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void HasOnly(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element has comparable options.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void HasComparableOptions(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if something is a certain value.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void Is(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is like a certain value.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void Like(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is not like a certain value.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void NotLike(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    public final IElement FindElement(ParameterObject parameterObject) {
//        return adapter.FindElement(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
//    }
//
//    /**
//     * Finds all <see cref="IWebElementAdapter">IWebElementAdapters</see> within the current product
//     * using the given mechanism.
//     *
//     * @param parameterObject Framework element.
//     * @return A <see cref="ReadOnlyCollection{T}"/> of all <see cref="IWebElementAdapter">IWebElementAdapters</see> matching the current criteria, or an empty list if nothing matches.
//     * @throws IllegalArgumentException If <paramref name="frameworkElement"/> is <see langword="null"/>.
//     */
//    public final Collection<IElement> FindElements(ParameterObject parameterObject) {
//        return adapter.FindElements(parameterObject.getGuid(), parameterObject.getWeb().getFindIBy());
//    }
//
//    /**
//     * Gets the innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
//     *
//     * @param parameterObject Framework element.
//     * @return The innerText of the element, without any leading or trailing whitespace, and with other whitespace collapsed.
//     */
//    public final String GetElementText(ParameterObject parameterObject) {
//        return parameterObject.getWeb().getWebElement().GetText(parameterObject.getGuid());
//    }
//
//    /**
//     * Captures and image.
//     *
//     * @param parameterObject The element in question.
//     */
//    public final void CaptureImage(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Gets the scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The current scroll position.
//     */
//    public final int GetScrollPosition(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Sets the scroll position.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    public final void SetScrollPosition(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Gets the maximum scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The maximum scroll position.
//     */
//    public final int GetMaximumScrollPosition(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Gets the minimum scroll position.
//     *
//     * @param parameterObject The parameter object.
//     * @return The minimum scroll position.
//     */
//    public final int GetMinimumScrollPosition(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Waits until the control element exists.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void WaitForControlExist(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Waits until the parent control contains a certain number of occurances of the child element.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void WaitForControlContains(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Closes window.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void Close(ParameterObject parameterObject) {
//        adapter.Close(parameterObject.getGuid());
//    }
//
//    /**
//     * Quits thing thing.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void Quit(ParameterObject parameterObject) {
//        adapter.Quit(parameterObject.getGuid());
//    }
//
//    /**
//     * Adds a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void AddCookie(ParameterObject parameterObject) {
//        if (parameterObject.getWeb().getCookie() == null) {
//            throw new IllegalArgumentException("cookie");
//        }
//
//        adapter.AddCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookie());
//    }
//
//    /**
//     * Deletes all cookies.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void DeleteAllCookiesCommand(ParameterObject parameterObject) {
//        adapter.DeleteAllCookiesCommand(parameterObject.getGuid());
//    }
//
//    /**
//     * Deletes a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void DeleteCookie(ParameterObject parameterObject) {
//        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
//            throw new IllegalArgumentException("cookieName");
//        }
//
//        adapter.DeleteCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookieName());
//    }
//
//    /**
//     * Gets all cookies.
//     *
//     * @param parameterObject The parameter object.
//     * @return List of cookies.
//     */
//    public final List<IWebCookie> GetAllCookies(ParameterObject parameterObject) {
//        return adapter.GetAllCookies(parameterObject.getGuid());
//    }
//
//    /**
//     * Gets a cookie.
//     *
//     * @param parameterObject The parameter object.
//     * @return The cookie adapter.
//     */
//    public final IWebCookie GetCookie(ParameterObject parameterObject) {
//        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
//            throw new IllegalArgumentException("cookieName");
//        }
//
//        return adapter.GetCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookieName());
//    }
//
//    /**
//     * Modifies a cookie.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void ModifyCookie(ParameterObject parameterObject) {
//        if (StringUtils.isBlank(parameterObject.getWeb().getCookieName())) {
//            throw new IllegalArgumentException("cookieName");
//        }
//
//        adapter.ModifyCookie(parameterObject.getGuid(), parameterObject.getWeb().getCookieName(), parameterObject.getWeb().getValue());
//    }
//
//    /**
//     * Verify the title.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void VerifyTitle(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Verify the url.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void VerifyUrl(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Scroll to top of page.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void ScrollToTop(ParameterObject parameterObject) {
//        adapter.ScrollToTop(parameterObject.getGuid());
//    }
//
//    /**
//     * Scroll to the end of the page.
//     *
//     * @param parameterObject The parameter object.
//     */
//    public final void ScrollToEnd(ParameterObject parameterObject) {
//        adapter.ScrollToEnd(parameterObject.getGuid());
//    }
//
//    /**
//     * Clears browser storage.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void ClearBrowserStorage(ParameterObject parameterObject) {
//        ExecuteScript(parameterObject);
//    }
//
//    /**
//     * Switches to a window by the title.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    public final String SwitchToWindowByTitle(ParameterObject parameterObject) {
//        if (parameterObject.getWeb().getWindowTitle() == null) {
//            throw new IllegalArgumentException("windowTitle");
//        }
//
//        if (parameterObject.getWeb().getWindowTitle().length() == 0) {
//            throw new IllegalArgumentException(Resources.getString("Argument_StringZeroLength") + " = windowTitle");
//        }
//
//        return adapter.SwitchToWindowByTitle(parameterObject.getGuid(), parameterObject.getWeb().getWindowTitle());
//    }
//
//    /**
//     * Switches to the main window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void SwitchToMainWindow(ParameterObject parameterObject) {
//
//    }
//
//    /**
//     * Switches to a window by the url.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    public final String SwitchToWindowByUrl(ParameterObject parameterObject) {
//        return adapter.SwitchToWindowByUrl(parameterObject.getGuid(), parameterObject.getWeb().getUrlString());
//    }
//
//    /**
//     * Checks if a window does not exist by the title.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void WindowDoesNotExistByTitle(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if a window does not exist by the url.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void WindowDoesNotExistByUrl(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Maximizes the window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void Maximize(ParameterObject parameterObject) {
//        adapter.Maximize(parameterObject.getGuid());
//    }
//
//    /**
//     * Resizes the window.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void Resize(ParameterObject parameterObject) {
//        adapter.Resize(parameterObject.getGuid(), parameterObject.getWeb().getSize());
//    }
//
//    /**
//     * Goes back.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void GoBack(ParameterObject parameterObject) {
//        adapter.Back(parameterObject.getGuid());
//    }
//
//    /**
//     * Goes forward.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void GoForward(ParameterObject parameterObject) {
//        adapter.Forward(parameterObject.getGuid());
//    }
//
//    /**
//     * Goes to a Url.
//     *
//     * @param parameterObject The paramter object.
//     * @return The current handler after the change.
//     */
//    public final String GoToUrl(ParameterObject parameterObject) {
//        return adapter.GoToUrl(parameterObject.getGuid(), parameterObject.getWeb().getUrl());
//    }
//
//    /**
//     * Refreshs the page.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void Refresh(ParameterObject parameterObject) {
//        adapter.Refresh(parameterObject.getGuid());
//    }
//
//    /**
//     * Appends a query string.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void AppendQueryString(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Grid Not Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void GridNotExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Grid Exists.
//     *
//     * @param parameterObject Parameter Object.
//     * @return Grid Index.
//     */
//    @Override
//    public int GridExists(ParameterObject parameterObject) {
//        return 0;
//    }
//
//    /**
//     * Row Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void RowExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Row Not Exists.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void RowNotExists(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Refresh frame.
//     *
//     * @param parameterObject The paramter object.
//     */
//    public final void RefreshFrame(ParameterObject parameterObject) {
//        adapter.RefreshFrame(parameterObject.getGuid());
//    }
//
//    /**
//     * Gets the browser type.
//     *
//     * @param parameterObject The paramter object.
//     * @return The browser type.
//     */
//    public final BrowserType GetBrowserType(ParameterObject parameterObject) {
//        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.appName; return browserType;");
//        String name = ExecuteScript(parameterObject).toString();
//        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.appVersion; return browserType;");
//        String version = ExecuteScript(parameterObject).toString();
//        parameterObject.getWeb().setScript("var browserType = \"\" + navigator.userAgent; return browserType;");
//        String userAgent = ExecuteScript(parameterObject).toString();
//
//        if (name.toLowerCase().contains("internet") && name.toLowerCase().contains("explorer")) {
//            return BrowserType.InternetExplorer;
//        } else if (version.toLowerCase().contains("chrome")) {
//            return BrowserType.Chrome;
//        } else if (userAgent.contains("Firefox")) {
//            return BrowserType.Firefox;
//        }
//
//        throw new RuntimeException("Browser type not recognized");
//    }
//
//    /**
//     * Gets client rects.
//     *
//     * @param parameterObject The paramter object.
//     * @return A client rect.
//     */
//    @Override
//    public ClientRects GetClientRects(ParameterObject parameterObject) {
//        return null;
//    }
//
//    /**
//     * Gets the element attribute.
//     *
//     * @param parameterObject The paramter object.
//     * @return The element attribute.
//     */
//    @Override
//    public String GetElementAttribute(ParameterObject parameterObject) {
//        return null;
//    }
//
//    /**
//     * Edits the menu navigation scroll.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void EditMenuNavigationScroll(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Fires change event.
//     *
//     * @param parameterObject The parameter object.
//     */
//    @Override
//    public void FireChangeEvent(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * JQuery before or after.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void JQueryBeforeOrAfter(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Open file dialog.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void OpenFileDialog(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Select file dialog.
//     *
//     * @param parameterObject The paramter object.
//     */
//    @Override
//    public void SelectFileDialog(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is not read only.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void NotReadOnly(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * Checks if the element is read only.
//     *
//     * @param parameterObject Parameter Object.
//     */
//    @Override
//    public void ReadOnly(ParameterObject parameterObject) {
//        throw new UnsupportedOperationException();
//    }
//
//    public String GetTitle(ParameterObject parameterObject) {
//        return adapter.GetTitle(parameterObject.getGuid());
//    }
//
//    public URL GetURL(ParameterObject parameterObject) {
//        return adapter.GetUrl(parameterObject.getGuid());
//    }
//
//    public Collection<String> GetWindowHandles(ParameterObject parameterObject) {
//        return adapter.GetWindowHandles(parameterObject.getGuid());
//    }
//
//    @Override
//    public String GetPageSource() {
//        return adapter.GetPageSource(UUID.randomUUID());
//    }
//
//    @Override
//    public Image GetScreenshot() {
//        return adapter.GetScreenshot(UUID.randomUUID());
//    }
}
