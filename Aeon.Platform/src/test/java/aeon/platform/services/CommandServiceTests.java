package aeon.platform.services;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.models.Selector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CommandServiceTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private CommandService commandService;

    private Class[] parametersEmpty;
    private Class[] parameters;
    private Class[] parametersIBy;

    private List<Object> args;
    private List<Object> argsIBy;

    private Selector selector;

    @Mock private Selector selectorMock;

    @Mock private Constructor commandConsMock;
    @Mock private ExecuteCommandBody bodyMock;
    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;

    @Mock private CommandWithReturn commandWithReturnMock;
    @Mock private Command commandMock;

    @Before
    public void setUp() {
        commandService = new CommandService();

        parametersEmpty = new Class[0];

        parameters = new Class[1];
        parameters[0] = String.class;

        parametersIBy = new Class[2];
        parametersIBy[0] = IByWeb.class;
        parametersIBy[1] = ICommandInitializer.class;

        args = new ArrayList<>();
        args.add("https://google.com");

        argsIBy = new ArrayList<>();
        argsIBy.add("IByWeb");
        argsIBy.add("ICommandInitializer");

        selector = new Selector("a", "css");
    }

    @Test
    public void getCommandInstanceWebTest() throws Exception {
        Constructor constructor = commandService.getCommandInstance("GoToUrlCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.web.GoToUrlCommand", constructor.getName());
    }

    @Test
    public void createConstructorQuitCommandTest() throws Exception {
        Constructor constructor = commandService.getCommandInstance("QuitCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.QuitCommand", constructor.getName());
    }

    @Test
    public void getCommandInstanceMobileTest() throws Exception {
        Constructor constructor = commandService.getCommandInstance("SetLandscapeCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.mobile.SetLandscapeCommand", constructor.getName() );
    }

    @Test
    public void getCommandInstanceBadInputTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> commandService.getCommandInstance("a"));
    }

    @Test
    public void executeCommandTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parametersEmpty);
        when(bodyMock.getArgs()).thenReturn(null);
        when(bodyMock.getSelector()).thenReturn(null);
        when(commandConsMock.getDeclaringClass()).thenReturn(Command.class);
        when(commandConsMock.newInstance(new Object[0])).thenReturn(commandMock);

        Object object = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(2)).getDeclaringClass();
        verify(commandConsMock, times(1)).newInstance();
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);

        Assert.assertNull(object);
    }

    @Test
    public void executeCommandWithReturnTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
        when(bodyMock.getArgs()).thenReturn(args);
        when(bodyMock.getSelector()).thenReturn(null);
        when(commandConsMock.getDeclaringClass()).thenReturn(CommandWithReturn.class);
        when(commandConsMock.newInstance("https://google.com")).thenReturn(commandWithReturnMock);
        when(commandExecutionFacadeMock.execute(automationInfoMock, commandWithReturnMock)).thenReturn("GoToUrlCommand Successful");

        Object object = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(1)).getDeclaringClass();
        verify(commandConsMock, times(1)).newInstance("https://google.com");
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandWithReturnMock);

        Assert.assertEquals("GoToUrlCommand Successful", object);
    }

    @Test
    public void executeCommandIByTest() throws Exception {
        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);

        when(commandConsMock.getParameterTypes()).thenReturn(parametersIBy);
        when(bodyMock.getArgs()).thenReturn(argsIBy);
        when(bodyMock.getSelector()).thenReturn(selector);
        when(selectorMock.getType()).thenReturn(null);
        when(selectorMock.getValue()).thenReturn(null);
        when(commandConsMock.getDeclaringClass()).thenReturn(WebControlCommand.class);
        when(commandConsMock.newInstance(captor.capture())).thenReturn(commandMock);

        Object object = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(2)).getDeclaringClass();
        verify(commandConsMock, times(1)).newInstance(captor.capture());
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);

        Assert.assertNull(object);
    }

    @Test
    public void executeCommandNullInputTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
        when(bodyMock.getArgs()).thenReturn(null);
        when(bodyMock.getSelector()).thenReturn(null);

        Assertions.assertThrows(Exception.class,
                () -> commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock));
    }

    @Test
    public void executeCommandNullSelectorTest() {
        when(commandConsMock.getParameterTypes()).thenReturn(parametersIBy);
        when(bodyMock.getArgs()).thenReturn(argsIBy);
        when(bodyMock.getSelector()).thenReturn(null);

        Assertions.assertThrows(NullPointerException.class,
                () -> commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock));
    }
}
