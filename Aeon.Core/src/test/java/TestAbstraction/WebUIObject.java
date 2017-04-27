package TestAbstraction;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.ICommandExecutionFacade;
import aeon.core.command.execution.commands.CloseCommand;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.QuitCommand;
import aeon.core.command.execution.commands.web.*;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IWebAdapter;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import aeon.core.framework.abstraction.drivers.AeonWebDriver;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import aeon.core.testabstraction.models.Browser;
import aeon.core.testabstraction.product.Configuration;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.swing.*;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by josephb on 1/24/2017.
 */

public class WebUIObject {
    // Variables
    private Browser browserObject;
    private AutomationInfo automationInfo;

    // Mocks
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Configuration configuration;
    @Mock
    private IAdapter adapter;
    @Mock
    private IDriver driver;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;
    @Mock
    private IWebCookie testCookie;
    @Mock
    private IBy selector;

    // Setup Methods
    @Before
    public void setUp() {
        automationInfo = new AutomationInfo(configuration, driver, adapter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        browserObject = new Browser(automationInfo);
    }

    // Test Methods
    @Test
    public void dismissAlert_CallsExecute() {
        browserObject.dismissAlert();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DismissAlertCommand.class));
    }

    @Test
    public void acceptAlert_CallsExecute() {
        browserObject.acceptAlert();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(AcceptAlertCommand.class));
    }

    @Test
    public void deleteAllCookies_CallsExecute() {
        browserObject.deleteAllCookies();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DeleteAllCookiesCommand.class));
    }

    @Test
    public void deleteCookie_CallsExecute() {
        browserObject.deleteCookie("cookiename");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DeleteCookieCommand.class));
    }

    @Test
    public void getBrowserType_CallsExecute() {
        browserObject.getBrowserType();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetBrowserTypeCommand.class));
    }

    @Test
    public void getCookie_CallsExecute_VerifyCookie() {
        when(commandExecutionFacade.execute(any(AutomationInfo.class), any(CommandWithReturn.class))).thenReturn(testCookie);
        IWebCookie returnedCookie = browserObject.getCookie("Placeholder");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetCookieCommand.class));
        assertTrue(testCookie == returnedCookie);
    }

    @Test
    public void goBack_CallsExecute() {
        browserObject.goBack();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoBackCommand.class));
    }

    @Test
    public void goForward_CallsExecute() {
        browserObject.goForward();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoForwardCommand.class));
    }

    @Test
    public void goToUrl_CallsExecute() {
        browserObject.goToUrl("http://www.kindafunny.com");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoToUrlCommand.class));
    }

    @Test
    public void maximize_CallsExecute() {
        browserObject.maximize();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(MaximizeCommand.class));
    }

    @Test
    public void modifyCookie_CallsExecute() {
        browserObject.modifyCookie(testCookie.getName(), testCookie.getValue());
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ModifyCookieCommand.class));
    }

    @Test
    public void quit_CallsExecute() {
        browserObject.quit();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(QuitCommand.class));
    }

    @Test
    public void close_CallsExecute() {
        browserObject.close();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(CloseCommand.class));
    }

    @Test
    public void refresh_CallsExecute() {
        browserObject.refresh();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(RefreshCommand.class));
    }

    @Test
    public void resize_CallsExecute() {
        browserObject.resize(BrowserSize.Maximized);
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ResizeCommand.class));
    }

    @Test
    public void scrollToTop_CallsExecute() {
        browserObject.scrollToTop();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ScrollToTopCommand.class));
    }

    @Test
    public void scrollToEnd_CallsExecute() {
        browserObject.scrollToEnd();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ScrollToEndCommand.class));
    }

    @Test
    public void sendKeysToAlert_ValidString_CallsExecuteWithCorrectKeys() {
        browserObject.sendKeysToAlert("Go Gators!");
        //TODO: Verify whether the correct keys are passed along to SendKeysToAlert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SendKeysToAlertCommand.class));
    }

    @Test
    public void switchToMainWindow_CallsExecute() {
        browserObject.switchToMainWindow();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToMainWindowCommand.class));
    }

    @Test
    public void switchToMainWindowWaitForPopups_CallsExecute() {
        browserObject.switchToMainWindow(true);
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToMainWindowCommand.class));
    }

    @Test
    public void switchToWindowByTitle_CallsExecute() {
        browserObject.switchToWindowByTitle("Kinda Funny");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToWindowByTitleCommand.class));
    }

    @Test
    public void switchToWindowByUrl_CallsExecute() {
        browserObject.switchToWindowByUrl("http://www.kindafunny.com");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToWindowByUrlCommand.class));
    }

    @Test
    public void verifyAlertExists_CallsExecute() {
        browserObject.verifyAlertExists();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertExistsCommand.class));
    }

    @Test
    public void verifyAlertNotExists_CallsExecute() {
        browserObject.verifyAlertNotExists();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertNotExistsCommand.class));
    }

    @Test
    public void getAlertText_CallsExecute_VerifyAlertText() {
        String alertText = "This is an alert";
        when(commandExecutionFacade.execute(any(AutomationInfo.class), any(CommandWithReturn.class))).thenReturn(alertText);
        String returnedText = browserObject.getAlertText();
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetAlertTextCommand.class));
        assertTrue(alertText.equals(returnedText));
    }

    @Test
    public void verifyAlertTextLike_CallsExecute() {
        browserObject.verifyAlertTextLike("test", true);
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertTextLikeCommand.class));
    }

    @Test
    public void verifyTitle_CallsExecute() {
        browserObject.verifyTitle("Title");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyTitleCommand.class));
    }

    @Test
    public void verifyUrl_CallsExecute() {
        browserObject.verifyURL("http://www.kindafunny.com");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyUrlCommand.class));
    }

    @Test
    public void verifyWindowDoesNotExistByTitle_CallsExecute() {
        browserObject.verifyWindowDoesNotExistByTitle("Title");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(WindowDoesNotExistByTitleCommand.class));
    }

    @Test
    public void verifyWindowDoesNotExistByUrl_CallsExecute() {
        browserObject.verifyWindowDoesNotExistByUrl("http://kindafunny.com");
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(WindowDoesNotExistByUrlCommand.class));
    }

    @Test
    public void clickAllElements_CallsExecute() {
        browserObject.clickAllElementsCommand(selector);
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ClickAllElementsCommand.class));
    }
}
