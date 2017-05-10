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
    public IDriver configure(IAdapter adapter) {
        this.adapter = (IWebAdapter) adapter;
        return this;
    }

    @Override
    public WebControl findElement(IBy selector) {
        return adapter.findElement(selector);
    }

    @Override
    public Collection<WebControl> findElements(IBy selector) {
        return adapter.findElements(selector);
    }

    @Override
    public void click(WebControl webControl) {
        adapter.click(webControl);
    }

    @Override
    public void doubleClick(WebControl element) {
        adapter.doubleClick(element);
    }

    @Override
    public void scrollElementIntoView(WebControl control) {
        adapter.scrollElementIntoView(control);
    }

    @Override
    public void scrollToTop() {
        adapter.scrollToTop();
    }

    @Override
    public void scrollToEnd() {
        adapter.scrollToEnd();
    }

    @Override
    public void switchToDefaultContent() {
        adapter.switchToDefaultContent();
    }

    @Override
    public void focusWindow() {
        adapter.focusWindow();
    }

    @Override
    public void switchToFrame(IBy selector) {
        adapter.switchToFrame(selector);
    }

    @Override
    public String getElementTagName(WebControl element) {
        return adapter.getElementTagName(element);
    }

    @Override
    public Object executeScript(String script) {
        return adapter.executeScript(script);
    }

    @Override
    public void clearElement(WebControl element) {
        adapter.clearElement(element);
    }

    @Override
    public void addCookie(IWebCookie cookie) {
        adapter.addCookie(cookie);
    }

    @Override
    public void deleteCookie(String cookie) {
        adapter.deleteCookie(cookie);
    }

    @Override
    public void deleteAllCookies() {
        adapter.deleteAllCookies();
    }

    @Override
    public void goBack() {
        adapter.back();
    }

    @Override
    public void goForward() {
        adapter.forward();
    }

    @Override
    public String goToUrl(URL url) {
        return adapter.goToUrl(url);
    }

    @Override
    public void maximize() {
        adapter.maximize();
    }

    @Override
    public void refresh() {
        adapter.refresh();
    }

    @Override
    public void chooseSelectElementByValue(WebControl element, String value) {
        adapter.chooseSelectElementByValue(element, value);
    }

    @Override
    public void chooseSelectElementByText(WebControl element, String value) {
        adapter.chooseSelectElementByText(element, value);
    }

    @Override
    public void clickElement(WebControl element) {
        adapter.clickElement(element);
    }

    @Override
    public void sendKeysToElement(WebControl element, String value) {
        adapter.sendKeysToElement(element, value);
    }

    @Override
    public String getElementAttribute(WebControl element, String value) {
        return adapter.getElementAttribute(element, value);
    }

    @Override
    public void switchToMainWindow(String mainWindowHandle, Boolean waitForAllPopupWindowsToClose) {
        adapter.switchToMainWindow(mainWindowHandle, waitForAllPopupWindowsToClose);
    }

    @Override
    public String switchToWindowByTitle(String title) {
        return adapter.switchToWindowByTitle(title);
    }

    @Override
    public String switchToWindowByUrl(String url) {
        return adapter.switchToWindowByUrl(url);
    }

    @Override
    public void resize(Size size) {
        adapter.resize(size);
    }

    @Override
    public Image getScreenshot() {
        return adapter.getScreenshot();
    }

    @Override
    public String getSource() {
        return adapter.getPageSource();
    }

    @Override
    public void close() {
        adapter.close();
    }

    @Override
    public void quit() {
        adapter.quit();
    }

    @Override
    public void acceptAlert() {
        adapter.acceptAlert();
    }

    @Override
    public void dismissAlert() {
        adapter.dismissAlert();
    }

    @Override
    public String getAlertText() {
        return adapter.getAlertText();
    }

    @Override
    public void blur(WebControl element) {
        adapter.blur(element);
    }

    @Override
    public void rightClick(WebControl element) {
        adapter.rightClick(element);
    }

    @Override
    public void check(WebControl element) {
        adapter.checkElement(element);
    }

    @Override
    public void unCheck(WebControl element) {
        adapter.unCheckElement(element);
    }

    @Override
    public void clickAndHold(WebControl element, int duration) {
        adapter.clickAndHold(element, duration);
    }

    @Override
    public void isElementEnabled(WebControl element) {
        adapter.isElementEnabled(element);
    }

    @Override
    public void exists(WebControl element) {
        adapter.exists(element);
    }

    @Override
    public void notExists(WebControl element) {
        adapter.notExists(element);
    }

    @Override
    public void hasOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.elementHasOptions(element, options, optgroup, select);
    }

    @Override
    public void doesNotHaveOptions(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.elementDoesNotHaveOptions(element, options, optgroup, select);
    }

    @Override
    public void openFileDialog(IBy selector) {
        adapter.openFileDialog(selector);
    }

    @Override
    public void selectFileDialog(IBy selector, String path) {
        adapter.selectFileDialog(selector, path);
    }

    @Override
    public void uploadFileDialog(IBy selector, String path) {
        adapter.uploadFileDialog(selector, path);
    }

    @Override
    public void verifyAlertExists() {
        adapter.verifyAlertExists();
    }

    @Override
    public void verifyAlertNotExists() {
        adapter.verifyAlertNotExists();
    }

    @Override
    public void sendKeysToAlert(String keysToSend) {
        adapter.sendKeysToAlert(keysToSend);
    }

    @Override
    public void dragAndDrop(WebControl dropElement, IBy targetElement) {
        adapter.dragAndDrop(dropElement, targetElement);
    }

    @Override
    public void clickAllElements(IBy elementsBy) {
        adapter.clickAllElements(elementsBy);
    }

    @Override
    public void mouseOut(WebControl element) {
        adapter.mouseOut(element);
    }

    @Override
    public void mouseOver(WebControl element) {
        adapter.mouseOver(element);
    }

    @Override
    public void set(WebControl element, WebSelectOption option, String setValue) {
        adapter.set(element, option, setValue);
    }

    @Override
    public void setBodyValueByJavaScript(WebControl element, String value) {
        adapter.setBodyValueByJavaScript(element, value);
    }

    @Override
    public void setTextByJavaScript(WebControl element, String value) {
        adapter.setTextByJavaScript(element, value);
    }

    @Override
    public void setDivValueByJavaScript(WebControl element, String value) {
        adapter.setDivValueByJavaScript(element, value);
    }

    @Override
    public void hasOptionsInOrder(WebControl element, String[] options, String optgroup, WebSelectOption select) {
        adapter.elementHasOptionsInOrder(element, options, optgroup, select);
    }

    @Override
    public void hasNumberOfOptions(WebControl element, int optnumber, String optgroup) {
        adapter.hasNumberOfOptions(element, optnumber, optgroup);
    }

    @Override
    public void hasAllOptionsInOrder(WebControl element, CompareType compare, String optGroup) {
        adapter.hasAllOptionsInOrder(element, compare, optGroup);
    }

    @Override
    public Collection<IWebCookie> getAllCookies() {
        return adapter.getAllCookies();
    }

    @Override
    public String windowDoesNotExistByTitle(String windowTitle) {
        return adapter.windowDoesNotExistByTitle(windowTitle);
    }

    @Override
    public String windowDoesNotExistByUrl(String url) {
        return adapter.windowDoesNotExistByUrl(url);
    }

    @Override
    public void modifyCookie(String name, String value) {
        adapter.modifyCookie(name, value);
    }

    @Override
    public IWebCookie getCookie(String name) {
        return adapter.getCookie(name);
    }

    @Override
    public void has(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.has(element, messages, selector, option, attribute);
    }

    @Override
    public void hasLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.hasLike(element, messages, selector, option, attribute);
    }

    @Override
    public void doesNotHave(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.doesNotHave(element, messages, selector, option, attribute);
    }

    @Override
    public void doesNotHaveLike(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.doesNotHaveLike(element, messages, selector, option, attribute);
    }

    @Override
    public void hasOnly(WebControl element, String[] messages, String selector, ComparisonOption option, String attribute) {
        adapter.hasOnly(element, messages, selector, option, attribute);
    }

    @Override
    public void is(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.is(element, value, option, attribute);
    }

    @Override
    public void isLike(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.isLike(element, value, option, attribute);
    }

    @Override
    public void isElementDisabled(WebControl element) {
        adapter.isElementDisabled(element);
    }

    @Override
    public void notSelected(WebControl element) {
        adapter.notSelected(element);
    }

    @Override
    public void notVisible(WebControl element) {
        adapter.notVisible(element);
    }

    @Override
    public void selected(WebControl element) {
        adapter.selected(element);
    }

    @Override
    public void visible(WebControl element) {
        adapter.visible(element);
    }

    //
    @Override
    public void verifyAlertText(String comparingText) {
        adapter.verifyAlertText(comparingText);
    }

    @Override
    public void verifyAlertTextLike(String comparingText, boolean caseSensitive) {
        adapter.verifyAlertTextLike(comparingText, caseSensitive);
    }

    @Override
    public void verifyTitle(String comparingTitle) {
        adapter.verifyTitle(comparingTitle);
    }

    @Override
    public void verifyURL(URL comparingURL) {
        adapter.verifyURL(comparingURL);
    }

    @Override
    public void datesApproximatelyEqual(WebControl element, String attributeName, DateTime expected, Period delta) {
        adapter.datesApproximatelyEqual(element, attributeName, expected, delta);
    }

    @Override
    public BrowserType getBrowserType() {
        return adapter.getBrowserType();
    }

    @Override
    public void isNotLike(WebControl element, String value, ComparisonOption option, String attribute) {
        adapter.isNotLike(element, value, option, attribute);
    }

    @Override
    public ClientRects getClientRects(WebControl element) {
        return adapter.getClientRects(element);
    }

    @Override
    public void pressKeyboardKey(WebControl element, KeyboardKey key) {
        adapter.pressKeyboardKey(element, key);
    }
}
