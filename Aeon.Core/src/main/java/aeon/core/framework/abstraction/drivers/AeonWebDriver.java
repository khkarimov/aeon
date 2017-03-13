package aeon.core.framework.abstraction.drivers;

import com.sun.glass.ui.Size;
import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.awt.*;
import java.net.URL;
import java.util.Collection;

/**
 * Web framework adapter.
 */
public class AeonWebDriver implements IWebDriver {
    private IWebAdapter adapter;

    /**
     * Initializes a new instance of the AeonWebDriver class.
     */
    public AeonWebDriver() {
    }

    @Override
    public IDriver Configure(IAdapter adapter) {
        this.adapter = (IWebAdapter) adapter;
        return this;
    }

    @Override
    public WebControl FindElement(IBy selector) {
        return adapter.FindElement(selector);
    }

    @Override
    public Collection<WebControl> FindElements(IBy selector) {
        return adapter.FindElements(selector);
    }

    @Override
    public void Click(WebControl webControl) {
        adapter.Click(webControl);
    }

    @Override
    public void DoubleClick(WebControl element) {
        adapter.DoubleClick(element);
    }

    @Override
    public void ScrollElementIntoView(WebControl control) {
        adapter.ScrollElementIntoView(control);
    }

    @Override
    public void ScrollToTop() {
        adapter.ScrollToTop();
    }

    @Override
    public void ScrollToEnd() {
        adapter.ScrollToEnd();
    }

    @Override
    public void SwitchToDefaultContent() {
        adapter.SwitchToDefaultContent();
    }

    @Override
    public void FocusWindow() {
        adapter.FocusWindow();
    }

    @Override
    public void SwitchToFrame(IBy selector) {
        adapter.SwitchToFrame(selector);
    }

    @Override
    public String GetElementTagName(WebControl element) {
        return adapter.GetElementTagName(element);
    }

    @Override
    public Object ExecuteScript(String script) {
        return adapter.ExecuteScript(script);
    }

    @Override
    public void ClearElement(WebControl element) {
        adapter.ClearElement(element);
    }

    @Override
    public void AddCookie(IWebCookie cookie) {
        adapter.AddCookie(cookie);
    }

    @Override
    public void DeleteCookie(String cookie) {
        adapter.DeleteCookie(cookie);
    }

    @Override
    public void DeleteAllCookies() {
        adapter.DeleteAllCookies();
    }

    @Override
    public void GoBack() {
        adapter.Back();
    }

    @Override
    public void GoForward() {
        adapter.Forward();
    }

    @Override
    public String GoToUrl(URL url) {
        return adapter.GoToUrl(url);
    }

    @Override
    public void Maximize() {
        adapter.Maximize();
    }

    @Override
    public void Refresh() {
        adapter.Refresh();
    }

    @Override
    public void ChooseSelectElementByValue(WebControl element, String value) {
        adapter.ChooseSelectElementByValue(element, value);
    }

    @Override
    public void ChooseSelectElementByText(WebControl element, String value) {
        adapter.ChooseSelectElementByText(element, value);
    }

    @Override
    public void ClickElement(WebControl element) {
        adapter.ClickElement(element);
    }

    @Override
    public void SendKeysToElement(WebControl element, String value) {
        adapter.SendKeysToElement(element, value);
    }

    @Override
    public String GetElementAttribute(WebControl element, String value) {
        return adapter.GetElementAttribute(element, value);
    }

    @Override
    public void SwitchToMainWindow(String mainWindowHandle, Boolean waitForAllPopupWindowsToClose) {
        adapter.SwitchToMainWindow(mainWindowHandle, waitForAllPopupWindowsToClose);
    }

    @Override
    public String SwitchToWindowByTitle(String title) {
        return adapter.SwitchToWindowByTitle(title);
    }

    @Override
    public String SwitchToWindowByUrl(String url) {
        return adapter.SwitchToWindowByUrl(url);
    }

    @Override
    public void Resize(Size size) {
        adapter.Resize(size);
    }

    @Override
    public Image GetScreenshot() {
        return adapter.GetScreenshot();
    }

    @Override
    public String GetSource() {
        return adapter.GetPageSource();
    }

    @Override
    public void Close() {
        adapter.Close();
    }

    @Override
    public void Quit() {
        adapter.Quit();
    }

    @Override
    public void AcceptAlert() {
        adapter.AcceptAlert();
    }

    @Override
    public void DismissAlert() {
        adapter.DismissAlert();
    }

    @Override
    public String GetAlertText() {
        return adapter.GetAlertText();
    }

    @Override
    public void Blur(WebControl element) {
        adapter.Blur(element);
    }

    @Override
    public void RightClick(WebControl element) {
        adapter.RightClick(element);
    }

    @Override
    public void Check(WebControl element) {
        adapter.CheckElement(element);
    }

    @Override
    public void UnCheck(WebControl element) {
        adapter.UnCheckElement(element);
    }

    @Override
    public void ClickAndHold(WebControl element, int duration) {
        adapter.ClickAndHold(element, duration);
    }

    @Override
    public void IsElementEnabled(WebControl element) {
        adapter.IsElementEnabled(element);
    }

    @Override
    public void Exists(WebControl element) {
        adapter.Exists(element);
    }

    @Override
    public void NotExists(WebControl element) {
        adapter.NotExists(element);
    }

    @Override
    public void HasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.ElementHasOptions(element, options, optgroup, select);
    }

    @Override
    public void DoesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.ElementDoesNotHaveOptions(element, options, optgroup, select);
    }

    @Override
    public void OpenFileDialog(IBy selector) {
        adapter.OpenFileDialog(selector);
    }

    @Override
    public void SelectFileDialog(IBy selector, String path) {
        adapter.SelectFileDialog(selector, path);
    }

    @Override
    public void UploadFileDialog(IBy selector, String path) {
        adapter.UploadFileDialog(selector, path);
    }

    @Override
    public void VerifyAlertExists() {
        adapter.VerifyAlertExists();
    }

    @Override
    public void VerifyAlertNotExists() {
        adapter.VerifyAlertNotExists();
    }

    @Override
    public void SendKeysToAlert(String keysToSend) {
        adapter.SendKeysToAlert(keysToSend);
    }

    @Override
    public void DragAndDrop(WebControl dropElement, IBy targetElement) {
        adapter.DragAndDrop(dropElement, targetElement);
    }

    @Override
    public void ClickAllElements(IBy elementsBy) {
        adapter.ClickAllElements(elementsBy);
    }

    @Override
    public void MouseOut(WebControl element) {
        adapter.MouseOut(element);
    }

    @Override
    public void MouseOver(WebControl element) {
        adapter.MouseOver(element);
    }

    @Override
    public void Set(WebControl element, WebSelectOption option, String setValue) {
        adapter.Set(element, option, setValue);
    }

    @Override
    public void SetBodyValueByJavaScript(WebControl element, String value) {
        adapter.SetBodyValueByJavaScript(element, value);
    }

    @Override
    public void SetTextByJavaScript(WebControl element, String value) {
        adapter.SetTextByJavaScript(element, value);
    }

    @Override
    public void SetDivValueByJavaScript(WebControl element, String value) {
        adapter.SetDivValueByJavaScript(element, value);
    }

    @Override
    public void HasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.ElementHasOptionsInOrder(element, options, optgroup, select);
    }

    @Override
    public void HasNumberOfOptions(WebControl element, int optnumber, String optgroup) {
        adapter.HasNumberOfOptions(element, optnumber, optgroup);
    }

    @Override
    public void HasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup) {
        adapter.HasAllOptionsInOrder(element, compare, optGroup);
    }

    @Override
    public Collection<IWebCookie> GetAllCookies() {
        return adapter.GetAllCookies();
    }

    @Override
    public String WindowDoesNotExistByTitle(String windowTitle) {
        return adapter.WindowDoesNotExistByTitle(windowTitle);
    }

    @Override
    public String WindowDoesNotExistByUrl(String url) {
        return adapter.WindowDoesNotExistByUrl(url);
    }

    @Override
    public void ModifyCookie(String name, String value) {
        adapter.ModifyCookie(name, value);
    }

    @Override
    public IWebCookie GetCookie(String name) {
        return adapter.GetCookie(name);
    }

    @Override
    public void Has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.Has(element, messages, selector, option, attribute);
    }

    @Override
    public void HasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.HasLike(element, messages, selector, option, attribute);
    }

    @Override
    public void DoesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.DoesNotHave(element, messages, selector, option, attribute);
    }

    @Override
    public void DoesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.DoesNotHaveLike(element, messages, selector, option, attribute);
    }

    @Override
    public void HasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.HasOnly(element, messages, selector, option, attribute);
    }

    @Override
    public void Is(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.Is(element, value, option, attribute);
    }

    @Override
    public void IsLike(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.IsLike(element, value, option, attribute);
    }

    @Override
    public void IsElementDisabled(WebControl element) {
        adapter.IsElementDisabled(element);
    }

    @Override
    public void NotSelected(WebControl element) {
        adapter.NotSelected(element);
    }

    @Override
    public void NotVisible(WebControl element) {
        adapter.NotVisible(element);
    }

    @Override
    public void Selected(WebControl element) {
        adapter.Selected(element);
    }

    @Override
    public void Visible(WebControl element) {
        adapter.Visible(element);
    }

    //
    @Override
    public void VerifyAlertText(String comparingText) {
        adapter.VerifyAlertText(comparingText);
    }

    @Override
    public void VerifyAlertTextLike(String comparingText, boolean caseSensitive) {
        adapter.VerifyAlertTextLike(comparingText, caseSensitive);
    }

    @Override
    public void VerifyTitle(String comparingTitle) {
        adapter.VerifyTitle(comparingTitle);
    }

    @Override
    public void VerifyURL(URL comparingURL) {
        adapter.VerifyURL(comparingURL);
    }

    @Override
    public void DatesApproximatelyEqual(WebControl element, String attributeName, DateTime expected, Period delta) {
        adapter.DatesApproximatelyEqual(element, attributeName, expected, delta);
    }

    @Override
    public BrowserType GetBrowserType() {
        return adapter.GetBrowserType();
    }

    @Override
    public void IsNotLike(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.IsNotLike(element, value, option, attribute);
    }

    @Override
    public ClientRects GetClientRects(WebControl element) {
        return adapter.GetClientRects(element);
    }

    @Override
    public void PressKeyboardKey(WebControl element, KeyboardKey key) {
        adapter.PressKeyboardKey(element, key);
    }
}
