package aeon.core.testabstraction.models;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.WebElement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ComponentTests {

    private class ComponentChild extends Component{
        public ComponentChild(AutomationInfo info, IByWeb selector, IByWeb... switchMechanism) {
            super(info, selector, switchMechanism);
        }
    }

    @Mock
    AutomationInfo automationInfo;

    @Mock
    IByWeb selector;

    @Mock
    IByWeb switchMechanism;

    private Component component;

    @Test
    public void Component_callsSuper(){
        // Arrange

        // Act
        component = new ComponentChild(automationInfo,selector,switchMechanism);

        // Assert
        assertTrue(component instanceof WebElement);

    }

    @Test
    public void Component_nullSwitchMechanism_callsSuper(){
        // Arrange

        // Act
        component = new ComponentChild(automationInfo,selector);

        // Assert
        assertTrue(component instanceof WebElement);
    }






}
