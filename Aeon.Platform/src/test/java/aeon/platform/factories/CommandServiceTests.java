package aeon.platform.factories;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.extensions.IProductTypeExtension;
import aeon.platform.http.models.ExecuteCommandBody;
import aeon.platform.http.models.Selector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class CommandServiceTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private CommandService commandService;

    private List<Object> args;
    private List<Object> argsIBy;

    @Mock private Supplier<List<IProductTypeExtension>> supplierMock;
    @Mock private List<IProductTypeExtension> extensionsMock;
    @Mock private IProductTypeExtension extensionMock;

    @Mock private Selector selectorMock;
    @Mock private ExecuteCommandBody bodyMock;
    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;

    @Mock private CommandWithReturn commandWithReturnMock;
    @Mock private Command commandMock;

    @Before
    public void setUp() {
        commandService = new CommandService(supplierMock);

        args = new ArrayList<>();
        args.add("https://google.com");

        argsIBy = new ArrayList<>();
        argsIBy.add("IByWeb");
        argsIBy.add("ICommandInitializer");
    }

    @Test
    public void executeCommandTest() throws Exception {
        when(bodyMock.getArgs()).thenReturn(null);
        when(bodyMock.getSelector()).thenReturn(null);
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(extensionMock);
        when(extensionMock.createCommand("QuitCommand", null, null, null))
                .thenReturn(commandMock);

        Object object = commandService.executeCommand("QuitCommand", bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(supplierMock, times(1)).get();
        verify(extensionsMock, times(1)).size();
        verify(extensionsMock, times(1)).get(0);
        verify(extensionMock, times(1)).createCommand("QuitCommand", null, null, null);
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);

        Assert.assertNull(object);
    }

    @Test
    public void executeCommandWithReturnTest() throws Exception {
        when(bodyMock.getArgs()).thenReturn(args);
        when(bodyMock.getSelector()).thenReturn(null);
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(extensionMock);
        when(extensionMock.createCommand("GoToUrlCommand", args, null, null)).thenReturn(commandWithReturnMock);
        when(commandExecutionFacadeMock.execute(automationInfoMock, commandWithReturnMock)).thenReturn("GoToUrlCommand Successful");

        Object object = commandService.executeCommand("GoToUrlCommand", bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(supplierMock, times(1)).get();
        verify(extensionsMock, times(1)).size();
        verify(extensionsMock, times(1)).get(0);
        verify(extensionMock, times(1)).createCommand("GoToUrlCommand", args, null, null);
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandWithReturnMock);

        Assert.assertNotNull(object);
        Assert.assertEquals("GoToUrlCommand Successful", object);
    }

    @Test
    public void executeCommandIByTest() throws Exception {
        when(bodyMock.getArgs()).thenReturn(argsIBy);
        when(bodyMock.getSelector()).thenReturn(selectorMock);
        when(selectorMock.getValue()).thenReturn("a");
        when(selectorMock.getType()).thenReturn("css");
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(extensionMock);
        when(extensionMock.createCommand("ClickCommand", argsIBy, "a", "css")).thenReturn(commandMock);

        Object object = commandService.executeCommand("ClickCommand", bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(selectorMock, times(1)).getValue();
        verify(selectorMock, times(1)).getType();
        verify(supplierMock, times(1)).get();
        verify(extensionsMock, times(1)).size();
        verify(extensionsMock, times(1)).get(0);
        verify(extensionMock, times(1)).createCommand("ClickCommand", argsIBy, "a", "css");
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);

        Assert.assertNull(object);
    }

    @Test
    public void executeCommandNullTest() {
        when(bodyMock.getArgs()).thenReturn(null);
        when(bodyMock.getSelector()).thenReturn(null);
        when(supplierMock.get()).thenReturn(extensionsMock);
        when(extensionsMock.size()).thenReturn(1);
        when(extensionsMock.get(0)).thenReturn(extensionMock);
        when(extensionMock.createCommand("ClickCommand", argsIBy, "a", "css")).thenReturn(null);

        Assertions.assertThrows(Exception.class,
                () -> commandService.executeCommand("GoToUrlCommand", bodyMock, automationInfoMock, commandExecutionFacadeMock));
    }
}
