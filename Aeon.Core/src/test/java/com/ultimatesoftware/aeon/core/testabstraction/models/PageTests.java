package com.ultimatesoftware.aeon.core.testabstraction.models;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class PageTests {

    @Mock
    private AutomationInfo automationInfo;

    static class PageStub extends Page {
        PageStub(AutomationInfo automationInfo) {
            super(automationInfo);
        }
    }

    @Test
    void constructor_setsAutomationInfo() {

        // Arrange

        // Act
        Page page = new PageStub(this.automationInfo);

        // Assert
        assertEquals(this.automationInfo, page.automationInfo);
    }
}
