package aeon.core.command.execution.commands.web;

    import aeon.core.command.execution.commands.initialization.ICommandInitializer;
    import aeon.core.common.ComparisonOption;
    import aeon.core.common.web.interfaces.IBy;
    import aeon.core.framework.abstraction.controls.web.WebControl;
    import aeon.core.framework.abstraction.drivers.IWebDriver;
    import org.junit.*;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnit;
    import org.mockito.junit.MockitoRule;
    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.verify;

public class HasOnlyCommandTests {

    private HasOnlyCommand hasOnlyCommand;

    private String [] messages = {"test", "test1", "test2", "test3"};
    private String childSelector = "Child";
    private String attribute = "attribute";

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IBy selector;
    @Mock
    private ICommandInitializer initializer;
    @Mock
    private IWebDriver driver;
    @Mock
    private WebControl control;

    @Before
    public void setup(){ hasOnlyCommand = new HasOnlyCommand(selector, initializer, messages, childSelector, ComparisonOption.Text, attribute); }

    @Test
    public void HasAllOptionsInOrderCommandTestsInOrder(){

        // Arrange

        // Act
        hasOnlyCommand.commandDelegate(driver, control);

        // Verify
        verify(driver, times(1)).hasOnly(control, messages, childSelector, ComparisonOption.Text, attribute);

    }
}