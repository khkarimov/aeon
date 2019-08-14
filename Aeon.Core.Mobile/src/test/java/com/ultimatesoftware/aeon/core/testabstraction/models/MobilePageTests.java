package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.command.execution.ICommandExecutionFacade;
import com.ultimatesoftware.aeon.core.command.execution.commands.mobile.SwitchToWebViewCommand;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class MobilePageTests {

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private ICommandExecutionFacade commandExecutionFacade;

    @Captor
    private ArgumentCaptor<SwitchToWebViewCommand> switchToWebViewCommandArgumentCaptor;

    static class MobilePageStub extends MobilePage {
        MobilePageStub(AutomationInfo automationInfo, IByWeb... switchMechanism) {
            super(automationInfo, switchMechanism);
        }

        MobilePageStub(IByWeb webViewSelector, AutomationInfo automationInfo, IByWeb... switchMechanism) {
            super(webViewSelector, automationInfo, switchMechanism);
        }
    }

    @Test
    void constructor_setsAutomationInfo() {

        // Arrange

        // Act
        Page page = new MobilePageStub(this.automationInfo);

        // Assert
        assertEquals(this.automationInfo, page.automationInfo);
    }

    @Test
    void constructor_setsAutomationInfoAndSwitchMechanism() {

        // Arrange
        IByWeb[] switchMechanism = new IByWeb[0];

        // Act
        MobilePage mobilePage = new MobilePageStub(this.automationInfo, switchMechanism);

        // Assert
        assertEquals(this.automationInfo, mobilePage.automationInfo);
        assertEquals(switchMechanism, mobilePage.switchMechanism);
    }

    @Test
    void switchToWebView_webViewSelectorIsSet_callsSwitchToWebViewWithWebViewSelector() {

        // Arrange
        IByWeb webViewSelector = By.cssSelector(".webview");
        IByWeb[] switchMechanism = new IByWeb[0];
        MobilePage mobilePage = new MobilePageStub(webViewSelector, this.automationInfo, switchMechanism);
        when(this.automationInfo.getCommandExecutionFacade()).thenReturn(this.commandExecutionFacade);

        // Act
        mobilePage.switchToWebView();

        // Assert
        assertEquals(this.automationInfo, mobilePage.automationInfo);
        assertEquals(switchMechanism, mobilePage.switchMechanism);
        verify(this.commandExecutionFacade, times(1)).execute(eq(this.automationInfo), this.switchToWebViewCommandArgumentCaptor.capture());
        assertEquals(webViewSelector, this.switchToWebViewCommandArgumentCaptor.getValue().getSelector());
    }

    @Test
    void switchToWebView_webViewSelectorIsNotSet_callsSwitchToWebViewWithNull() {

        // Arrange
        IByWeb[] switchMechanism = new IByWeb[0];
        MobilePage mobilePage = new MobilePageStub(this.automationInfo, switchMechanism);
        when(this.automationInfo.getCommandExecutionFacade()).thenReturn(this.commandExecutionFacade);

        // Act
        mobilePage.switchToWebView();

        // Assert
        assertEquals(this.automationInfo, mobilePage.automationInfo);
        assertEquals(switchMechanism, mobilePage.switchMechanism);
        verify(this.commandExecutionFacade, times(1)).execute(eq(this.automationInfo), this.switchToWebViewCommandArgumentCaptor.capture());
        assertNull(this.switchToWebViewCommandArgumentCaptor.getValue().getSelector());
    }

    @Test
    void switchToMainWebView_webViewSelectorIsSet_callsSwitchToWebViewWithNull() {

        // Arrange
        IByWeb webViewSelector = By.cssSelector(".webview");
        IByWeb[] switchMechanism = new IByWeb[0];
        MobilePage mobilePage = new MobilePageStub(webViewSelector, this.automationInfo, switchMechanism);
        when(this.automationInfo.getCommandExecutionFacade()).thenReturn(this.commandExecutionFacade);

        // Act
        mobilePage.switchToMainWebView();

        // Assert
        assertEquals(this.automationInfo, mobilePage.automationInfo);
        assertEquals(switchMechanism, mobilePage.switchMechanism);
        verify(this.commandExecutionFacade, times(1)).execute(eq(this.automationInfo), this.switchToWebViewCommandArgumentCaptor.capture());
        assertNull(this.switchToWebViewCommandArgumentCaptor.getValue().getSelector());
    }
}
