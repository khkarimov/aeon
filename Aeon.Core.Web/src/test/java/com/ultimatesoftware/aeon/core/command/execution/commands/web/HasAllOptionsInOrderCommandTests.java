package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.CompareType;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.controls.web.WebControl;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class HasAllOptionsInOrderCommandTests {

    private HasAllOptionsInOrderCommand hasAllOptionsInOrderCommand;

    private CompareType compare = CompareType.ASCENDING_BY_TEXT;
    private String optGroup = "Test";

    @Mock
    private IByWeb selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @BeforeEach
    public void setup() {
        hasAllOptionsInOrderCommand = new HasAllOptionsInOrderCommand(selector, initializer, compare, optGroup);
    }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder() {
        // Arrange

        // Act
        hasAllOptionsInOrderCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasAllOptionsInOrder(control, compare, optGroup);
    }
}
