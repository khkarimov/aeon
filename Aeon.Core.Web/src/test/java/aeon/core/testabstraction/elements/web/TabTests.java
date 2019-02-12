package aeon.core.testabstraction.elements.web;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
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
    Iterable<IByWeb> switchMechanism;

    private Tab tab;

    @Test
    public void Tab_callsSuper(){

        // Arrange

        // Act
        tab = new Tab(automationInfo,selector,switchMechanism);

        // Assert
        assertTrue(tab instanceof WebElement);
    }

    @Test
    public void Tab_nullSwitchMechanism_callsSuper(){

        // Arrange

        // Act
        tab = new Tab(automationInfo,selector);

        // Assert
        assertTrue(tab instanceof WebElement);
    }
}
