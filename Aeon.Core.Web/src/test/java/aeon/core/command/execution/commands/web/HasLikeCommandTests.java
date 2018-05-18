package aeon.core.command.execution.commands.web;

import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.common.ComparisonOption;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.controls.web.WebControl;
import aeon.core.framework.abstraction.drivers.IWebDriver;

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
@MockitoSettings(strictness = Strictness.LENIENT)
public class HasLikeCommandTests {

    private HasLikeCommand hasLikeCommand;

    private String [] messages = {"test", "test1", "test2", "test3"};
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
    public void setup(){ hasLikeCommand = new HasLikeCommand(selector, initializer, messages, childSelector, ComparisonOption.Text, attribute); }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder(){
        // Arrange

        // Act
        hasLikeCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasLike(control, messages, childSelector, ComparisonOption.Text, attribute);
    }
}