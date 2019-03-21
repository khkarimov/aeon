package com.ultimatesoftware.aeon.core.framework.abstraction.drivers;

import com.ultimatesoftware.aeon.core.common.CompareType;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
import com.ultimatesoftware.aeon.core.common.KeyboardKey;
import com.ultimatesoftware.aeon.core.common.web.ClientRects;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IWebAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;

import java.awt.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

/**
 * Web framework adapter.
 */
public class AeonWebDriver implements IWebDriver {

    private IWebAdapter adapter;
    private Configuration configuration;

    /**
     * Initializes a new instance of the AeonWebDriver class.
     */
    public AeonWebDriver() {
    }

    @Override
    public IDriver configure(IAdapter adapter, Configuration configuration) {
        this.adapter = (IWebAdapter) adapter;
        this.configuration = configuration;
        return this;
    }

    @Override
    public WebControl findElement(IByWeb selector) {
        return adapter.findElement(selector);
    }

    @Override
    public Collection<WebControl> findElements(IByWeb selector) {
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
        boolean scrollElementIntoView = configuration.getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false);
        if (scrollElementIntoView) {
            adapter.scrollElementIntoView(control);
        }
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
    public void switchToFrame(IByWeb selector) {
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
    public Object executeAsyncScript(String script) {
        return adapter.executeAsyncScript(script);
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
    public void resize(Dimension size) {
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
    public void dragAndDrop(WebControl dropElement, IByWeb targetElement) {
        adapter.dragAndDrop(dropElement, targetElement);
    }

    @Override
    public void clickAllElements(IByWeb elementsBy) {
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
    public void selectFile(WebControl control, String path) {
        Path actualPath = Paths.get(path);
        if (!actualPath.isAbsolute()) {
            actualPath = Paths.get(System.getProperty("user.dir"), path);
        }
        adapter.selectFile(control, actualPath.toString());
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
    public void datesApproximatelyEqual(WebControl element, String attributeName, LocalDate expected, Period delta) {
        adapter.datesApproximatelyEqual(element, attributeName, expected, delta);
    }

    @Override
    public IBrowserType getBrowserType() {
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
