package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.CommandWithReturn;
import com.ultimatesoftware.aeon.core.command.execution.commands.QuitCommand;
import com.ultimatesoftware.aeon.core.command.execution.commands.web.*;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import com.ultimatesoftware.aeon.core.common.web.BrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IBrowserType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.IWebCookie;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class BrowserTests {

    // Variables
    private Browser browserObject;
    private AutomationInfo automationInfo;

    // Mocks
    @Mock
    private WebConfiguration configuration;
    @Mock
    private IAdapter adapter;
    @Mock
    private IDriver driver;
    @Mock
    private ICommandExecutionFacade commandExecutionFacade;
    @Mock
    private IWebCookie testCookie;
    @Mock
    private IByWeb selector;

    // Setup Methods
    @BeforeEach
    void setUp() {
        automationInfo = new AutomationInfo(configuration, driver, adapter);
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        browserObject = new Browser(automationInfo);
    }

    // Test Methods
    @Test
    void dismissAlert_CallsExecute() {

        //Arrange

        //Act
        browserObject.dismissAlert();

        //Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DismissAlertCommand.class));
    }

    @Test
    void acceptAlert_CallsExecute() {

        // Arrange

        // Act
        browserObject.acceptAlert();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(AcceptAlertCommand.class));
    }

    @Test
    void deleteAllCookies_CallsExecute() {

        // Arrange

        // Act
        browserObject.deleteAllCookies();

        //Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DeleteAllCookiesCommand.class));
    }

    @Test
    void deleteCookie_CallsExecute() {

        // Arrange

        // Act
        browserObject.deleteCookie("cookiename");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(DeleteCookieCommand.class));
    }

    @Test
    void addCookie_CallsExecute() {

        // Arrange

        // Act
        browserObject.addCookie(testCookie);

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(AddCookieCommand.class));
    }

    @Test
    void getAllCookies_CallsExecute() {

        // Arrange

        // Act
        browserObject.getAllCookies();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetAllCookiesCommand.class));
    }

    @Test
    void verifyAlertText_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyAlertText("comparingText");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertTextCommand.class));
    }

    @Test
    void getBrowserType_CallsExecute() {

        // Arrange
        when(configuration.getBrowserType()).thenReturn(BrowserType.CHROME);

        // Act
        IBrowserType result = browserObject.getBrowserType();

        // Assert
        Assertions.assertEquals(BrowserType.CHROME, result);
    }

    @Test
    void getCookie_CallsExecute_VerifyCookie() {

        // Arrange
        when(commandExecutionFacade.execute(any(AutomationInfo.class), any(CommandWithReturn.class))).thenReturn(testCookie);

        // Act
        IWebCookie returnedCookie = browserObject.getCookie("Placeholder");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetCookieCommand.class));
        Assertions.assertSame(testCookie, returnedCookie);
    }

    @Test
    void goBack_CallsExecute() {

        // Arrange

        // Act
        browserObject.goBack();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoBackCommand.class));
    }

    @Test
    void goForward_CallsExecute() {

        // Arrange

        // Act
        browserObject.goForward();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoForwardCommand.class));
    }

    @Test
    void goToUrl_CallsExecute() {

        // Arrange

        // Act
        browserObject.goToUrl("http://www.kindafunny.com");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GoToUrlCommand.class));
    }

    @Test
    void maximize_CallsExecute() {

        // Arrange

        // Act
        browserObject.maximize();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(MaximizeCommand.class));
    }

    @Test
    void modifyCookie_CallsExecute() {

        // Arrange

        // Act
        browserObject.modifyCookie(testCookie.getName(), testCookie.getValue());

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ModifyCookieCommand.class));
    }

    @Test
    void quit_CallsExecute() {

        // Arrange

        // Act
        browserObject.quit();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(QuitCommand.class));
    }

    @Test
    void quitConfigurationReporting_CallsTestSucceeded() {

        // Arrange
        AutomationInfo automationInfoSpy = Mockito.spy(automationInfo);
        when(configuration.getBoolean(Configuration.Keys.REPORTING, true)).thenReturn(true);
        browserObject = new Browser(automationInfoSpy);

        // Act
        browserObject.quit();

        // Assert
        verify(automationInfoSpy, times(1)).testSucceeded();
    }

    @Test
    void close_CallsExecute() {

        // Arrange

        // Act
        browserObject.close();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(CloseCommand.class));
    }

    @Test
    void refresh_CallsExecute() {

        // Arrange

        // Act
        browserObject.refresh();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(RefreshCommand.class));
    }

    @Test
    void resize_CallsExecute() {

        // Arrange

        // Act
        browserObject.resize(BrowserSize.MAXIMIZED);

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ResizeCommand.class));
    }

    @Test
    void scrollToTop_CallsExecute() {

        // Arrange

        // Act
        browserObject.scrollToTop();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ScrollToTopCommand.class));
    }

    @Test
    void scrollToEnd_CallsExecute() {

        // Arrange

        // Act
        browserObject.scrollToEnd();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(ScrollToEndCommand.class));
    }

    @Test
    void sendKeysToAlert_ValidString_CallsExecuteWithCorrectKeys() {

        // Arrange

        // Act
        browserObject.sendKeysToAlert("Go Gators!");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SendKeysToAlertCommand.class));
    }

    @Test
    void switchToMainWindow_CallsExecute() {

        // Arrange

        // Act
        browserObject.switchToMainWindow();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToMainWindowCommand.class));
    }

    @Test
    void switchToMainWindowWaitForPopups_CallsExecute() {

        // Arrange

        // Act
        browserObject.switchToMainWindow(true);

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToMainWindowCommand.class));
    }

    @Test
    void switchToWindowByTitle_CallsExecute() {

        // Arrange

        // Act
        browserObject.switchToWindowByTitle("Kinda Funny");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToWindowByTitleCommand.class));
    }

    @Test
    void switchToWindowByUrl_CallsExecute() {

        // Arrange

        // Act
        browserObject.switchToWindowByUrl("http://www.kindafunny.com");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(SwitchToWindowByUrlCommand.class));
    }

    @Test
    void verifyAlertExists_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyAlertExists();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertExistsCommand.class));
    }

    @Test
    void verifyAlertNotExists_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyAlertNotExists();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertNotExistsCommand.class));
    }

    @Test
    void getAlertText_CallsExecute_VerifyAlertText() {

        // Arrange
        String alertText = "This is an alert";
        when(commandExecutionFacade.execute(any(AutomationInfo.class), any(CommandWithReturn.class))).thenReturn(alertText);


        // Act
        String returnedText = browserObject.getAlertText();

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(GetAlertTextCommand.class));
        Assertions.assertEquals(alertText, returnedText);
    }

    @Test
    void verifyAlertTextLike_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyAlertTextLike("test", true);

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyAlertTextLikeCommand.class));
    }

    @Test
    void verifyTitle_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyTitle("Title");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyTitleCommand.class));
    }

    @Test
    void verifyUrl_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyURL("http://www.kindafunny.com");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(VerifyUrlCommand.class));
    }

    @Test
    void verifyWindowDoesNotExistByTitle_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyWindowDoesNotExistByTitle("Title");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(WindowDoesNotExistByTitleCommand.class));
    }

    @Test
    void verifyWindowDoesNotExistByUrl_CallsExecute() {

        // Arrange

        // Act
        browserObject.verifyWindowDoesNotExistByUrl("http://kindafunny.com");

        // Assert
        verify(commandExecutionFacade, times(1)).execute(Mockito.eq(automationInfo), any(WindowDoesNotExistByUrlCommand.class));
    }
}
