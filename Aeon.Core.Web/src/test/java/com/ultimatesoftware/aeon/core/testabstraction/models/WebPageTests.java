package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class WebPageTests {

    @Mock
    private AutomationInfo automationInfo;

    static class WebPageStub extends WebPage {
        WebPageStub(AutomationInfo automationInfo, IByWeb... switchMechanism) {
            super(automationInfo, switchMechanism);
        }
    }

    @Test
    void constructor_setsAutomationInfo() {

        // Arrange

        // Act
        Page page = new WebPageStub(this.automationInfo);

        // Assert
        assertEquals(this.automationInfo, page.automationInfo);
    }

    @Test
    void constructor_setsAutomationInfoAndSwitchMechanism() {

        // Arrange
        IByWeb[] switchMechanism = new IByWeb[0];

        // Act
        WebPage webPage = new WebPageStub(this.automationInfo, switchMechanism);

        // Assert
        assertEquals(this.automationInfo, webPage.automationInfo);
        assertEquals(switchMechanism, webPage.switchMechanism);
    }
}
