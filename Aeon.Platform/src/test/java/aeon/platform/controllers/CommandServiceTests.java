//package aeon.platform.controllers;
//
//import aeon.core.command.execution.AutomationInfo;
//import aeon.core.command.execution.WebCommandExecutionFacade;
//import aeon.core.command.execution.commands.Command;
//import aeon.core.command.execution.commands.CommandWithReturn;
//import aeon.core.command.execution.commands.initialization.ICommandInitializer;
//import aeon.core.command.execution.commands.web.WebControlCommand;
//import aeon.core.common.web.interfaces.IByWeb;
//import aeon.core.framework.abstraction.drivers.IDriver;
//import aeon.platform.models.ExecuteCommandBody;
//import aeon.platform.models.Selector;
//import aeon.platform.services.CommandService;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.rules.ExpectedException;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.lang.reflect.Constructor;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
//import static org.mockito.Mockito.*;
//
//public class CommandServiceTests {
//
//    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
//    @Rule public ExpectedException expectedException = ExpectedException.none();
//
//    private CommandService commandService;
//
//    private Class[] parametersEmpty;
//    private Class[] parameters;
//    private Class[] parametersIBy;
//
//    private List<Object> args;
//    private List<Object> argsIBy;
//
//    private Selector selector;
//
//    @Mock private Selector selectorMock;
//
//    @Mock private Constructor commandConsMock;
//    @Mock private ExecuteCommandBody bodyMock;
//    @Mock private AutomationInfo automationInfoMock;
//    @Mock private WebCommandExecutionFacade commandExecutionFacadeMock;
//
//    @Mock private Function<IDriver, Object> commandDelegateMock;
//    @Mock private IDriver driverMock;
//
//    @Mock private CommandWithReturn commandWithReturnMock;
//    @Mock private Command commandMock;
//
//    @Before
//    public void setUp() {
//        commandService = new CommandService();
//
//        parametersEmpty = new Class[0];
//
//        parameters = new Class[1];
//        parameters[0] = String.class;
//
//        parametersIBy = new Class[2];
//        parametersIBy[0] = IByWeb.class;
//        parametersIBy[1] = ICommandInitializer.class;
//
//        args = new ArrayList<>();
//        args.add("https://google.com");
//
//        argsIBy = new ArrayList<>();
//        argsIBy.add("IByWeb");
//        argsIBy.add("ICommandInitializer");
//
//        selector = new Selector("a", "css");
//    }
//
//    @Test
//    public void createConstructorTest() throws Exception {
//        Constructor constructor = commandService.createConstructor("GoToUrlCommand");
//
//        Assert.assertEquals("aeon.core.command.execution.commands.web.GoToUrlCommand", constructor.getName());
//    }
//
//    @Test
//    public void createConstructorQuitCommandTest() throws Exception {
//        Constructor constructor = commandService.createConstructor("QuitCommand");
//
//        Assert.assertEquals("aeon.core.command.execution.commands.QuitCommand", constructor.getName());
//    }
//
//    @Test
//    public void createConstructorBadInputTest() {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> commandService.createConstructor("a"));
//    }
//
//    @Test
//    public void executeCommandTest() throws Exception {
//        when(commandConsMock.getParameterTypes()).thenReturn(parametersEmpty);
//        when(bodyMock.getArgs()).thenReturn(null);
//        when(bodyMock.getSelector()).thenReturn(null);
//        when(commandConsMock.getDeclaringClass()).thenReturn(Command.class);
//        when(commandConsMock.newInstance(new Object[0])).thenReturn(commandMock);
//
//        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);
//
//        verify(commandConsMock, times(1)).getParameterTypes();
//        verify(bodyMock, times(1)).getArgs();
//        verify(bodyMock, times(1)).getSelector();
//        verify(commandConsMock, times(2)).getDeclaringClass();
//        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);
//        verify(commandConsMock, times(1)).newInstance(new Object[0]);
//
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void executeCommandWithReturnTest() throws Exception {
//        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
//        when(bodyMock.getArgs()).thenReturn(args);
//        when(bodyMock.getSelector()).thenReturn(null);
//        when(commandConsMock.getDeclaringClass()).thenReturn(CommandWithReturn.class);
//        when(commandConsMock.newInstance("https://google.com")).thenReturn(commandWithReturnMock);
//        when(commandWithReturnMock.getCommandDelegate()).thenReturn(commandDelegateMock);
//        when(automationInfoMock.getDriver()).thenReturn(driverMock);
//        when(commandDelegateMock.apply(driverMock)).thenReturn(new Object());
//
//        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);
//
//        verify(commandConsMock, times(1)).getParameterTypes();
//        verify(bodyMock, times(1)).getArgs();
//        verify(bodyMock, times(1)).getSelector();
//        verify(commandConsMock, times(1)).getDeclaringClass();
//        verify(commandConsMock, times(1)).newInstance("https://google.com");
//        verify(commandWithReturnMock, times(1)).getCommandDelegate();
//        verify(automationInfoMock, times(1)).getDriver();
//        verify(commandDelegateMock, times(1)).apply(driverMock);
//        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandWithReturnMock);
//
//        Assert.assertNotNull(response.getBody());
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void executeCommandIByTest() throws Exception {
//        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);
//
//        when(commandConsMock.getParameterTypes()).thenReturn(parametersIBy);
//        when(bodyMock.getArgs()).thenReturn(argsIBy);
//        when(bodyMock.getSelector()).thenReturn(selector);
//        when(selectorMock.getType()).thenReturn(null);
//        when(selectorMock.getValue()).thenReturn(null);
//        when(commandConsMock.getDeclaringClass()).thenReturn(WebControlCommand.class);
//        when(commandConsMock.newInstance(captor.capture())).thenReturn(commandMock);
//
//        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);
//
//        verify(commandConsMock, times(1)).getParameterTypes();
//        verify(bodyMock, times(1)).getArgs();
//        verify(bodyMock, times(1)).getSelector();
//        verify(commandConsMock, times(2)).getDeclaringClass();
//        verify(commandConsMock, times(1)).newInstance(captor.capture());
//        verify(commandExecutionFacadeMock, times(1)).execute(automationInfoMock, commandMock);
//
//        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    public void executeCommandNullInputTest() throws Exception {
//        when(commandConsMock.getParameterTypes()).thenReturn(parameters);
//        when(bodyMock.getArgs()).thenReturn(null);
//        when(bodyMock.getSelector()).thenReturn(null);
//
//        ResponseEntity response = commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock);
//
//        verify(commandConsMock, times(1)).getParameterTypes();
//        verify(bodyMock, times(1)).getArgs();
//        verify(bodyMock, times(1)).getSelector();
//        verify(commandConsMock, times(0)).getDeclaringClass();
//        verify(commandExecutionFacadeMock, times(0)).execute(automationInfoMock, commandWithReturnMock);
//        verify(commandConsMock, times(0)).newInstance("https://google.com");
//
//        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//    }
//
//    @Test
//    public void executeCommandNullSelectorTest() {
//        when(commandConsMock.getParameterTypes()).thenReturn(parametersIBy);
//        when(bodyMock.getArgs()).thenReturn(argsIBy);
//        when(bodyMock.getSelector()).thenReturn(null);
//
//        Assertions.assertThrows(NullPointerException.class,
//                () -> commandService.executeCommand(commandConsMock, bodyMock, automationInfoMock, commandExecutionFacadeMock));
//    }
//}
