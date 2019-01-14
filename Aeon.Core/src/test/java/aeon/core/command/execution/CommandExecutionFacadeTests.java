package aeon.core.command.execution;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CommandExecutionFacadeTests {


    @Mock
    private IDelegateRunner delegateRunner;

    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Command command;

    @Mock
    private CommandWithReturn returnCommand;

    @Mock
    private IDelegateRunnerFactory delegateRunnerFactory;

    private CommandExecutionFacade executionFacade;

    @BeforeEach
    public void setUp() {
        executionFacade = new CommandExecutionFacade(this.delegateRunnerFactory);
    }

    @Test
    public void execute_NullCommand_IllegalArgumentException() {
        //arrange
        command = null;
        //act
        assertThrows(IllegalArgumentException.class, () -> executionFacade.execute(automationInfo, command));
        //assert
    }

    @Test
    public void execute_ObjectNullCommand_IllegalArgumentException() {
        //arrange
        returnCommand = null;
        //act
        assertThrows(IllegalArgumentException.class, () -> executionFacade.execute(automationInfo, returnCommand));
        //assert
    }

    @Test
    public void execute_MockObjects_callsDelegateFactoryCreateInstance() {
        //arrange
        when(delegateRunnerFactory.createInstance(automationInfo)).thenReturn(mock(IDelegateRunner.class));

        //act
        executionFacade.execute(automationInfo, command);

        //assert
        verify(delegateRunnerFactory.createInstance(automationInfo), times(1)).execute(command.getCommandDelegate());

    }

    @Test
    public void execute_MockObjects_returnsDelegateFactoryCreateInstance() {
        //arrange
        when(delegateRunnerFactory.createInstance(automationInfo)).thenReturn(mock(IDelegateRunner.class));


        //act
        executionFacade.execute(automationInfo, returnCommand);

        //assert
        //verify(delegateRunner, times(1)).execute(returnCommand.getCommandDelegate());
        verify(delegateRunnerFactory.createInstance(automationInfo), times(1)).execute(refEq(returnCommand.getCommandDelegate()));

    }

}
