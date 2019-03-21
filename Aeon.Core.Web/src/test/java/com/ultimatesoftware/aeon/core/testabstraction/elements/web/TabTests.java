package com.ultimatesoftware.aeon.core.testabstraction.elements.web;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class TabTests {

    @Mock
    AutomationInfo automationInfo;

    @Mock
    IByWeb selector;

    @Mock
    IByWeb switchMechanism;

    private Tab tab;

    @Test
    public void Tab_callsSuper() {

        // Arrange

        // Act
        tab = new Tab(automationInfo, selector, switchMechanism);

        // Assert
        assertTrue(tab instanceof WebElement);
    }

    @Test
    public void Tab_nullSwitchMechanism_callsSuper() {

        // Arrange

        // Act
        tab = new Tab(automationInfo, selector);

        // Assert
        assertTrue(tab instanceof WebElement);
    }
}
