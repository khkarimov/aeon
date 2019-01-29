package aeon.core.framework.abstraction.drivers;

import aeon.core.common.CompareType;
import aeon.core.common.ComparisonOption;
import aeon.core.common.KeyboardKey;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.ClientRects;
import aeon.core.common.web.WebSelectOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.WebConfiguration;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AeonWebDriverTests {

    private AeonWebDriver aeonWebDriver;

    @Mock
    private IWebAdapter adapter;
    @Mock
    private Configuration configuration;
    @Mock
    private IByWeb selector;
    @Mock
    private WebControl webControl;
    @Mock
    private IWebCookie cookie;
    @Mock
    private List<IWebCookie> cookies;
    @Mock
    private Image image;


    @BeforeEach
    void setUp() {
        aeonWebDriver = new AeonWebDriver();
        aeonWebDriver.configure(adapter, configuration);
    }

    @Test
    void findElement_givenWebControl_returnAdapterWebControl() {

        // Arrange

        // Act
        aeonWebDriver.findElement(selector);

        // Assert
        verify(adapter, times(1)).findElement(selector);
    }

    @Test
    void findElements_givenWebControl_returnAdapterWebControl() {

        // Arrange

        // Act
        aeonWebDriver.findElements(selector);

        // Assert
        verify(adapter, times(1)).findElements(selector);
    }

    @Test
    void click_givenWebControl_verifyAdapterClick() {

        // Arrange

        // Act
        aeonWebDriver.click(webControl);

        // Assert
        verify(adapter, times(1)).click(webControl);
    }

    @Test
    void doubleClick_givenWebControl_verifyAdapterDoubleClick() {

        // Arrange

        // Act
        aeonWebDriver.doubleClick(webControl);

        //Assert
        verify(adapter, times(1)).doubleClick(webControl);
    }

    @Test
    void scrollElementIntoView_whenScrollTrue_AdapterScroll() {

        // Arrange
        when(configuration.getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false)).thenReturn(true);

        // Act
        aeonWebDriver.scrollElementIntoView(webControl);

        // Assert
        verify(configuration, times(1)).getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false);
        verify(adapter, times(1)).scrollElementIntoView(webControl);
    }

    @Test
    void scrollElementIntoView_whenScrollFalse_AdapterNotScroll() {

        //Arrange
        when(configuration.getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false)).thenReturn(false);

        //Act
        aeonWebDriver.scrollElementIntoView(webControl);

        //Assert
        verify(configuration, times(1)).getBoolean(WebConfiguration.Keys.SCROLL_ELEMENT_INTO_VIEW, false);
        verifyZeroInteractions(adapter);
    }

    @Test
    void scrollToTop_verifyAdapterScrollToTop() {

        // Arrange

        // Act
        aeonWebDriver.scrollToTop();

        // Assert
        verify(adapter, times(1)).scrollToTop();
    }

    @Test
    void scrollToTop_verifyAdapterScrollToEnd() {

        // Arrange

        // Act
        aeonWebDriver.scrollToEnd();

        // Assert
        verify(adapter, times(1)).scrollToEnd();
    }

    @Test
    void switchToDefaultContent_verifyAdapterSwitch() {

        // Arrange

        // Act
        aeonWebDriver.switchToDefaultContent();

        // Assert
        verify(adapter, times(1)).switchToDefaultContent();
    }

    @Test
    void focusWindow_verifyAdapterFocus() {

        // Arrange

        // Act
        aeonWebDriver.focusWindow();

        //Assert
        verify(adapter, times(1)).focusWindow();
    }

    @Test
    void switchToFrame_verifyAdapterSwitch() {

        // Arrange

        // Act
        aeonWebDriver.switchToFrame(selector);

        // Assert
        verify(adapter, times(1)).switchToFrame(selector);
    }

    @Test
    void getElementTagName_verifyAdapterGetElement() {

        // Arrange
        when(adapter.getElementTagName(webControl)).thenReturn("Success");

        // Act
        String result = aeonWebDriver.getElementTagName(webControl);

        // Assert
        verify(adapter, times(1)).getElementTagName(webControl);
        assertEquals("Success", result);
    }

    @Test
    void executeScript_verifyAdapterExecuteScript() {

        // Arrange
        when(adapter.executeScript("Test")).thenReturn(true);

        // Act
        Object result = aeonWebDriver.executeScript("Test");

        // Assert
        verify(adapter, times(1)).executeScript("Test");
        assertEquals(true, result);
    }

    @Test
    void executeAsyncScript_verifyAdapterExecuteAsyncScript() {

        // Arrange
        when(adapter.executeAsyncScript("Test")).thenReturn(true);

        // Act
        Object result = aeonWebDriver.executeAsyncScript("Test");

        // Assert
        verify(adapter, times(1)).executeAsyncScript("Test");
        assertEquals(true, result);
    }

    @Test
    void clearElement_verifyAdapterClearElement() {

        // Arrange

        // Act
        aeonWebDriver.clearElement(webControl);

        // Assert
        verify(adapter, times(1)).clearElement(webControl);
    }

    @Test
    void addCookie_verifyAdapterAddCookie() {

        // Arrange

        // Act
        aeonWebDriver.addCookie(cookie);

        // Assert
        verify(adapter, times(1)).addCookie(cookie);
    }

    @Test
    void deleteCookie_verifyAdapterDeleteCookie() {

        // Arrange

        // Act
        aeonWebDriver.deleteCookie("Test");

        // Assert
        verify(adapter, times(1)).deleteCookie("Test");
    }

    @Test
    void deleteAllCookies_verifyAdapterDeleteAllCookies() {

        // Arrange

        // Act
        aeonWebDriver.deleteAllCookies();

        // Assert
        verify(adapter, times(1)).deleteAllCookies();
    }

    @Test
    void goBack_verifyAdapterBack() {

        // Arrange

        // Act
        aeonWebDriver.goBack();

        // Assert
        verify(adapter, times(1)).back();
    }

    @Test
    void goForward_verifyAdapterForward() {

        // Arrange

        // Act
        aeonWebDriver.goForward();

        // Assert
        verify(adapter, times(1)).forward();
    }

    @Test
    void goToURL_verifyAdapterGoToURL() throws MalformedURLException {

        // Arrange
        URL test = new URL("http://Test");
        when(adapter.goToUrl(test)).thenReturn("Success");

        // Act
        String result = aeonWebDriver.goToUrl(test);

        // Assert
        verify(adapter, times(1)).goToUrl(test);
        assertEquals("Success", result);
    }

    @Test
    void maximize_verifyAdapterMaximize() {

        // Arrange

        // Act
        aeonWebDriver.maximize();

        // Assert
        verify(adapter, times(1)).maximize();
    }

    @Test
    void refresh_verifyAdapterRefresh() {

        // Arrange

        // Act
        aeonWebDriver.refresh();

        // Assert
        verify(adapter, times(1)).refresh();
    }

    @Test
    void chooseSelectElementByValue_verifyAdapterChooseByValue() {

        // Arrange

        // Act
        aeonWebDriver.chooseSelectElementByValue(webControl, "Test");

        // Assert
        verify(adapter, times(1)).chooseSelectElementByValue(webControl, "Test");
    }

    @Test
    void chooseSelectElementByText_verifyAdapterChooseByText() {

        // Arrange

        // Act
        aeonWebDriver.chooseSelectElementByText(webControl, "Test");

        // Assert
        verify(adapter, times(1)).chooseSelectElementByText(webControl, "Test");
    }

    @Test
    void clickElement_verifyAdapterClickElement() {

        // Arrange

        // Act
        aeonWebDriver.clickElement(webControl);

        // Assert
        verify(adapter, times(1)).clickElement(webControl);
    }

    @Test
    void sendKeysToElement_verifyAdapterSendKeysToElement() {

        // Arrange

        // Act
        aeonWebDriver.sendKeysToElement(webControl, "Test");

        // Assert
        adapter.sendKeysToElement(webControl, "Test");
    }

    @Test
    void getElementAttribute_verifyAdapterGetElement() {

        // Arrange
        when(adapter.getElementAttribute(webControl, "Test")).thenReturn("Success");

        // Act
        String result = aeonWebDriver.getElementAttribute(webControl, "Test");

        // Assert
        verify(adapter, times(1)).getElementAttribute(webControl, "Test");
        assertEquals("Success", result);
    }

    @Test
    void switchToMainWindow_verifyAdapterSwitchToMainWindow() {

        // Arrange

        // Act
        aeonWebDriver.switchToMainWindow("Test", true);

        // Assert
        verify(adapter, times(1)).switchToMainWindow("Test", true);
    }

    @Test
    void switchToWindowByTitle_verifyAdapterSwitchToWindowsByTitle() {

        // Arrange
        when(adapter.switchToWindowByTitle("Test")).thenReturn("Success");

        // Act
        String result = aeonWebDriver.switchToWindowByTitle("Test");

        // Assert
        verify(adapter, times(1)).switchToWindowByTitle("Test");
        assertEquals("Success", result);
    }

    @Test
    void switchToWindowByUrl_verifyAdapterSwitchToWindowsByURL() {

        // Arrange
        when(adapter.switchToWindowByUrl("Test")).thenReturn("Success");

        // Act
        String result = aeonWebDriver.switchToWindowByUrl("Test");

        // Assert
        verify(adapter, times(1)).switchToWindowByUrl("Test");
        assertEquals("Success", result);
    }

    @Test
    void resize_verifyAdapterResize() {

        // Arrange
        Dimension size = new Dimension();

        // Act
        aeonWebDriver.resize(size);

        // Assert
        verify(adapter, times(1)).resize(size);
    }

    @Test
    void getScreenshot_verifyAdapterGetScreenshot() {

        // Arrange
        when(adapter.getScreenshot()).thenReturn(image);

        // Act
        Image result = aeonWebDriver.getScreenshot();

        // Assert
        verify(adapter, times(1)).getScreenshot();
        assertEquals(image, result);
    }

    @Test
    void getSource_verifyAdapterGetSource() {

        // Arrange
        when(adapter.getPageSource()).thenReturn("Success");

        // Act
        String result = aeonWebDriver.getSource();

        // Assert
        verify(adapter, times(1)).getPageSource();
        assertEquals("Success", result);
    }

    @Test
    void close_verifyAdapterClose() {

        // Arrange

        // Act
        aeonWebDriver.close();

        // Assert
        verify(adapter, times(1)).close();
    }

    @Test
    void quit_verifyAdapterQuit() {

        // Arrange

        // Act
        aeonWebDriver.quit();

        // Assert
        verify(adapter, times(1)).quit();
    }

    @Test
    void acceptAlert_verifyAdapterAcceptAlert() {

        // Arrange

        // Act
        aeonWebDriver.acceptAlert();

        // Assert
        verify(adapter, times(1)).acceptAlert();
    }

    @Test
    void dismissAlert_verifyAdapterDismissAlert() {

        // Arrange

        // Act
        aeonWebDriver.dismissAlert();

        // Assert
        verify(adapter, times(1)).dismissAlert();
    }

    @Test
    void getAlertText_verifyAdapterGetAlertText() {

        // Arrange
        when(adapter.getAlertText()).thenReturn("Success");

        // Act
        String result = aeonWebDriver.getAlertText();

        // Assert
        verify(adapter, times(1)).getAlertText();
        assertEquals("Success", result);
    }

    @Test
    void blur_verifyAdapterBlur() {

        // Arrange

        // Act
        aeonWebDriver.blur(webControl);

        // Assert
        verify(adapter, times(1)).blur(webControl);
    }

    @Test
    void rightClick_verifyAdapterRightClick() {

        // Arrange

        // Act
        aeonWebDriver.rightClick(webControl);

        // Assert
        verify(adapter, times(1)).rightClick(webControl);
    }

    @Test
    void check_verifyAdapterCheckElement() {

        // Arrange

        // Act
        aeonWebDriver.check(webControl);

        // Assert
        verify(adapter, times(1)).checkElement(webControl);
    }

    @Test
    void unCheck_verifyAdapterUnCheckElement() {

        // Arrange

        // Act
        aeonWebDriver.unCheck(webControl);

        // Assert
        verify(adapter, times(1)).unCheckElement(webControl);
    }

    @Test
    void clickAndHold_verifyAdapterClickAndHold() {

        // Arrange

        // Act
        aeonWebDriver.clickAndHold(webControl, 0);

        // Assert
        verify(adapter, times(1)).clickAndHold(webControl, 0);
    }

    @Test
    void isElementEnabled_verifyAdapterIsElementEnabled() {

        // Arrange

        // Act
        aeonWebDriver.isElementEnabled(webControl);

        // Assert
        verify(adapter, times(1)).isElementEnabled(webControl);
    }

    @Test
    void exists_verifyAdapterExists() {

        // Arrange

        // Act
        aeonWebDriver.exists(webControl);

        // Assert
        verify(adapter, times(1)).exists(webControl);
    }

    @Test
    void notExists_verifyAdapterNotExists() {

        // Arrange

        // Act
        aeonWebDriver.notExists(webControl);

        // Assert
        verify(adapter, times(1)).notExists(webControl);
    }

    @Test
    void hasOptions_verifyAdapterHasOptions() {

        // Arrange
        String[] options = new String[1];

        // Act
        aeonWebDriver.hasOptions(webControl, options, "Test", WebSelectOption.Value);

        // Assert
        verify(adapter, times(1)).elementHasOptions(webControl, options, "Test", WebSelectOption.Value);
    }

    @Test
    void doesNotHaveOptions_verifyAdapterDoesNotHaveOptions() {

        // Arrange
        String[] options = new String[1];

        // Act
        aeonWebDriver.doesNotHaveOptions(webControl, options, "Test", WebSelectOption.Value);

        // Assert
        verify(adapter, times(1)).elementDoesNotHaveOptions(webControl, options, "Test", WebSelectOption.Value);
    }

    @Test
    void verifyAlertExists_verifyAdapterVerifyAlertExists() {

        // Arrange

        // Act
        aeonWebDriver.verifyAlertExists();

        // Assert
        verify(adapter, times(1)).verifyAlertExists();
    }

    @Test
    void verifyAlertNotExists_verifyAdapterVerifyAlertNotExists() {

        // Arrange

        // Act
        aeonWebDriver.verifyAlertNotExists();

        // Assert
        verify(adapter, times(1)).verifyAlertNotExists();
    }

    @Test
    void sendKeysToAlert_verifyAdapter() {

        // Arrange

        // Act
        aeonWebDriver.sendKeysToAlert("Test");

        // Assert
        verify(adapter, times(1)).sendKeysToAlert("Test");
    }

    @Test
    void dragAndDrop_verifyAdapterDragAndDrop() {

        // Arrange

        // Act
        aeonWebDriver.dragAndDrop(webControl, selector);

        // Assert
        verify(adapter, times(1)).dragAndDrop(webControl, selector);
    }

    @Test
    void clickAllElements_verifyAdapterClickAllElements() {

        // Arrange

        // Act
        aeonWebDriver.clickAllElements(selector);

        // Assert
        verify(adapter, times(1)).clickAllElements(selector);
    }

    @Test
    void mouseOut_verifyAdapterMouseOut() {

        // Arrange

        // Act
        aeonWebDriver.mouseOut(webControl);

        // Assert
        verify(adapter, times(1)).mouseOut(webControl);
    }

    @Test
    void mouseOver_verifyAdapterMouesOver() {

        // Arrange

        // Act
        aeonWebDriver.mouseOver(webControl);

        // Assert
        verify(adapter, times(1)).mouseOver(webControl);
    }

    @Test
    void set_verifyAdapterSet() {

        // Arrange

        // Act
        aeonWebDriver.set(webControl, WebSelectOption.Value, "Test");

        // Assert
        verify(adapter, times(1)).set(webControl, WebSelectOption.Value, "Test");
    }

    @Test
    void setBodyValueByJavaScript_verifyAdapterSetBodyByJavaScript() {

        // Arrange

        // Act
        aeonWebDriver.setBodyValueByJavaScript(webControl, "Test");

        // Assert
        verify(adapter, times(1)).setBodyValueByJavaScript(webControl, "Test");
    }

    @Test
    void setTextByJavaScript_verifyAdapterSetTextByJavaScript() {

        // Arrange

        // Act
        aeonWebDriver.setTextByJavaScript(webControl, "Test");

        // Assert
        verify(adapter, times(1)).setTextByJavaScript(webControl, "Test");
    }

    @Test
    void setDivValueByJavaScript_verifyAdapterSetDivValueByJavaScript() {

        // Arrange

        // Act
        aeonWebDriver.setDivValueByJavaScript(webControl, "Test");

        // Assert
        verify(adapter, times(1)).setDivValueByJavaScript(webControl, "Test");
    }

    @Test
    void hasOptionsInOrder_verifyAdapterHasOptionsInOrder() {

        // Arrange
        String[] options = new String[1];

        // Act
        aeonWebDriver.hasOptionsInOrder(webControl, options, "Test", WebSelectOption.Value);

        // Assert
        verify(adapter, times(1)).elementHasOptionsInOrder(webControl, options, "Test", WebSelectOption.Value);
    }

    @Test
    void hasNumberOfOptions_verifyAdapterHasNumberOfOptions() {

        // Arrange

        // Act
        aeonWebDriver.hasNumberOfOptions(webControl, 0, "Test");

        // Assert
        verify(adapter, times(1)).hasNumberOfOptions(webControl, 0, "Test");
    }

    @Test
    void hasAllOptionsInOrder_verifyAdapterHasAllOptions() {

        // Arrange

        // Act
        aeonWebDriver.hasAllOptionsInOrder(webControl, CompareType.AscendingByText, "Test");

        // Assert
        verify(adapter, times(1)).hasAllOptionsInOrder(webControl, CompareType.AscendingByText, "Test");
    }

    @Test
    void getAllCookies_verifyAdapterGetAllCookies() {

        // Arrange
        when(adapter.getAllCookies()).thenReturn(cookies);

        // Act
        Collection<IWebCookie> result = aeonWebDriver.getAllCookies();

        // Assert
        verify(adapter, times(1)).getAllCookies();
        assertEquals(cookies, result);
    }

    @Test
    void windowDoesNotExistByTitle_verifyAdapterWindowDoesNotExistByTitle() {

        // Arrange
        when(adapter.windowDoesNotExistByTitle("Test")).thenReturn("Success");

        // Act
        String result = aeonWebDriver.windowDoesNotExistByTitle("Test");

        // Assert
        verify(adapter, times(1)).windowDoesNotExistByTitle("Test");
        assertEquals("Success", result);
    }

    @Test
    void windowDoesNotExistByUrl_verifyAdapterWindowDoesNotExistByUrl() {

        // Arrange
        when(adapter.windowDoesNotExistByUrl("Test")).thenReturn("Success");

        // Act
        String result = aeonWebDriver.windowDoesNotExistByUrl("Test");

        // Assert
        verify(adapter, times(1)).windowDoesNotExistByUrl("Test");
        assertEquals("Success", result);
    }

    @Test
    void selectFile_pathIsAbsolute_verifyAdapterSelectFile() {

        // Arrange
        String path = "/home/test";

        // Act
        aeonWebDriver.selectFile(webControl, path);

        // Assert
        assertTrue(Paths.get(path).isAbsolute());
        verify(adapter, times(1)).selectFile(webControl, path);
    }

    @Test
    void selectFile_pathIsNotAbsolute_verifyAdapterSelectFile() {

        // Arrange
        String path = "test";
        Path actualPath = Paths.get(System.getProperty("user.dir"), path);

        // Act
        aeonWebDriver.selectFile(webControl, path);

        // Assert
        assertFalse(Paths.get(path).isAbsolute());
        verify(adapter, times(1)).selectFile(webControl, actualPath.toString());
    }

    @Test
    void modifyCookies_verifyAdapterModifyCookie() {

        // Arrange

        // Act
        aeonWebDriver.modifyCookie("Test", "Success");

        // Assert
        verify(adapter, times(1)).modifyCookie("Test", "Success");
    }

    @Test
    void getCookie_verifyAdapterGetCookie() {

        // Arrange
        when(adapter.getCookie("Test")).thenReturn(cookie);

        // Act
        IWebCookie result = aeonWebDriver.getCookie("Test");

        // Assert
        verify(adapter, times(1)).getCookie("Test");
        assertEquals(cookie, result);
    }

    @Test
    void has_verifyAdapterHas() {

        // Arrange
        String[] messages = new String[1];

        // Act
        aeonWebDriver.has(webControl, messages, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).has(webControl, messages, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void hasLike_verifyAdapterHasLike() {

        // Arrange
        String[] messages = new String[1];

        // Act
        aeonWebDriver.hasLike(webControl, messages, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).hasLike(webControl, messages, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void doesNotHave_verifyAdapterDoesNotHave() {

        // Arrange
        String[] messages = new String[1];

        // Act
        aeonWebDriver.doesNotHave(webControl, messages, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).doesNotHave(webControl, messages, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void doesNotHaveLike_verifyAdapterDoesNotHaveLike() {

        // Arrange
        String[] messages = new String[1];

        // Act
        aeonWebDriver.doesNotHaveLike(webControl, messages, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).doesNotHaveLike(webControl, messages, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void hasOnly_verifyAdapterHasOnly() {

        // Arrange
        String[] messages = new String[1];

        // Act
        aeonWebDriver.hasOnly(webControl, messages, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).hasOnly(webControl, messages, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void is_verifyAdapterIs() {

        // Arrange

        // Act
        aeonWebDriver.is(webControl, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).is(webControl, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void isLike_verifyAdapterIsLike() {

        // Arrange

        // Act
        aeonWebDriver.isLike(webControl, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).isLike(webControl, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void isElementDisabled_verifyAdapterIsElementDisabled() {

        // Arrange

        // Act
        aeonWebDriver.isElementDisabled(webControl);

        // Assert
        verify(adapter, times(1)).isElementDisabled(webControl);
    }

    @Test
    void notSelected_verifyAdapterNotSelected() {

        // Arrange

        // Act
        aeonWebDriver.notSelected(webControl);

        // Assert
        verify(adapter, times(1)).notSelected(webControl);
    }

    @Test
    void notVisible_verifyAdapterNotSelected() {

        // Arrange

        // Act
        aeonWebDriver.notSelected(webControl);

        // Assert
        verify(adapter, times(1)).notSelected(webControl);
    }

    @Test
    void selected_verifyAdapterSelected() {

        // Arrange

        // Act
        aeonWebDriver.selected(webControl);

        // Assert
        verify(adapter, times(1)).selected(webControl);
    }

    @Test
    void visible_verifyAdapterVisible() {

        // Arrange

        // Act
        aeonWebDriver.visible(webControl);

        // Assert
        verify(adapter, times(1)).visible(webControl);
    }

    @Test
    void verifyAlertText_verifyAdapterVerifyAlertText() {

        // Arrange

        // Act
        aeonWebDriver.verifyAlertText("Test");

        // Assert
        verify(adapter, times(1)).verifyAlertText("Test");
    }

    @Test
    void verifyAlertTextLike_verifyAdapterVerifyAlertTextLike() {

        // Arrange

        // Act
        aeonWebDriver.verifyAlertTextLike("Test", true);

        // Assert
        verify(adapter, times(1)).verifyAlertTextLike("Test", true);
    }

    @Test
    void verifyTitle_verifyAdapterVerifyTitle() {

        // Arrange

        // Act
        aeonWebDriver.verifyTitle("Test");

        // Assert
        verify(adapter, times(1)).verifyTitle("Test");
    }

    @Test
    void verifyURL_verifyAdapterVerifyURL() throws MalformedURLException {

        // Arrange
        URL test = new URL("http://Test");

        // Act
        aeonWebDriver.verifyURL(test);

        // Assert
        verify(adapter, times(1)).verifyURL(test);
    }

    @Test
    void datesApproximatelyEqual_verifyAdapterDatesApproximatelyEqual() {

        // Arrange
        DateTime time = DateTime.now();

        // Act
        aeonWebDriver.datesApproximatelyEqual(webControl, "Test", time, Period.ZERO);

        // Assert
        verify(adapter, times(1)).datesApproximatelyEqual(webControl, "Test", time, Period.ZERO);
    }

    @Test
    void getBrowserType_verifyAdapterGetBrowserType() {

        // Arrange
        when(adapter.getBrowserType()).thenReturn(BrowserType.Chrome);

        // Act
        BrowserType result = aeonWebDriver.getBrowserType();

        // Assert
        verify(adapter, times(1)).getBrowserType();
        assertEquals(BrowserType.Chrome, result);
    }

    @Test
    void isNotLike_verifyAdapterIsNotLike() {

        // Arrange

        // Act
        aeonWebDriver.isNotLike(webControl, "Test", ComparisonOption.Text, "Test");

        // Assert
        verify(adapter, times(1)).isNotLike(webControl, "Test", ComparisonOption.Text, "Test");
    }

    @Test
    void getClientsRects_verifyAdapterGetClientRects() {

        // Arrange
        ClientRects expected = new ClientRects(0, 0, 0, 0);
        when(adapter.getClientRects(webControl)).thenReturn(expected);

        // Act
        ClientRects result = aeonWebDriver.getClientRects(webControl);

        // Assert
        verify(adapter, times(1)).getClientRects(webControl);
        assertEquals(expected, result);
    }

    @Test
    void pressKeyboardKey_verifyAdapterPressKeyboardKey() {

        // Arrange

        // Act
        aeonWebDriver.pressKeyboardKey(webControl, KeyboardKey.ENTER);

        // Assert
        verify(adapter, times(1)).pressKeyboardKey(webControl, KeyboardKey.ENTER);
    }
}