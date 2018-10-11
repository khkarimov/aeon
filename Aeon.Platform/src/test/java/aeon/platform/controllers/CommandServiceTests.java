package aeon.platform.controllers;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.ICommandInitializer;
import aeon.core.command.execution.commands.web.WebControlCommand;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.platform.models.ExecuteCommandBody;
import aeon.platform.models.Selector;
import aeon.platform.services.CommandService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CommandServiceTests {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException expectedException = ExpectedException.none();

    private CommandService commandService;

    private Class[] parameters;
    private Class[] parametersIBy;

    private List<Object> args;
    private List<Object> argsIBy;

    private Selector selector;

    @Mock private Constructor commandConsMock;
    @Mock private ExecuteCommandBody bodyMock;
    @Mock private AutomationInfo automationInfoMock;
    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;

    @Before
    public void setUp() {
        commandService = new CommandService();

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
    public void createConstructorTest() throws Exception {
        Constructor constructor = commandService.createConstructor("GoToUrlCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.web.GoToUrlCommand", constructor.getName());
    }

    @Test
    public void createConstructorQuitCommandTest() throws Exception {
        Constructor constructor = commandService.createConstructor("QuitCommand");

        Assert.assertEquals("aeon.core.command.execution.commands.QuitCommand", constructor.getName());
    }

    @Test
    public void createConstructorBadInputTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> commandService.createConstructor("a"));
    }

    @Test
    public void executeCommandTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
        when(bodyMock.getArgs()).thenReturn(args);
        when(bodyMock.getSelector()).thenReturn(null);
        when(commandConsMock.getDeclaringClass()).thenReturn(CommandWithReturn.class);

        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(1)).getDeclaringClass();
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, (CommandWithReturn) null);
        verify(commandConsMock, times(1)).newInstance("https://google.com");

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeCommandIByTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parametersIBy);
        when(bodyMock.getArgs()).thenReturn(argsIBy);
        when(bodyMock.getSelector()).thenReturn(selector);
        when(commandConsMock.getDeclaringClass()).thenReturn(WebControlCommand.class);

        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(2)).getDeclaringClass();
        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, (Command) null);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void executeCommandNullInputTest() throws Exception {
        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
        when(bodyMock.getArgs()).thenReturn(null);
        when(bodyMock.getSelector()).thenReturn(null);
        when(commandConsMock.getDeclaringClass()).thenReturn(CommandWithReturn.class);

        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);

        verify(commandConsMock, times(1)).getParameterTypes();
        verify(bodyMock, times(1)).getArgs();
        verify(bodyMock, times(1)).getSelector();
        verify(commandConsMock, times(0)).getDeclaringClass();
        verify(commandExecutionFacadeMock, times(0)).execute(automationInfoMock, (CommandWithReturn) null);
        verify(commandConsMock, times(0)).newInstance("https://google.com");

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
