package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.command.execution.commands.initialization.ICommandInitializer;
import com.ultimatesoftware.aeon.core.common.ComparisonOption;
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
public class HasOnlyCommandTests {

    private HasOnlyCommand hasOnlyCommand;

    private String[] messages = {"test", "test1", "test2", "test3"};
    private String childSelector = "Child";
    private String attribute = "attribute";

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
        hasOnlyCommand = new HasOnlyCommand(selector, initializer, messages, childSelector, ComparisonOption.TEXT, attribute);
    }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder() {
        // Arrange

        // Act
        hasOnlyCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasOnly(control, messages, childSelector, ComparisonOption.TEXT, attribute);
    }
}
