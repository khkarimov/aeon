package aeon.core.command.execution;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunner;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
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

        // Arrange
        command = null;

        // Act
        Executable executable = () -> executionFacade.execute(automationInfo, command);

        // Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void execute_ObjectNullCommand_IllegalArgumentException() {

        // Arrange
        returnCommand = null;

        // Act
        Executable executable = () -> executionFacade.execute(automationInfo, returnCommand);

        // Assert
        assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void execute_MockObjects_callsDelegateFactoryCreateInstance() {

        // Arrange
        when(delegateRunnerFactory.createInstance(automationInfo)).thenReturn(mock(IDelegateRunner.class));

        // Act
        executionFacade.execute(automationInfo, command);

        // Assert
        verify(delegateRunnerFactory.createInstance(automationInfo), times(1)).execute(command.getCommandDelegate());

    }

    @Test
    public void execute_MockObjects_returnsDelegateFactoryCreateInstance() {
        
        // Arrange
        when(delegateRunnerFactory.createInstance(automationInfo)).thenReturn(mock(IDelegateRunner.class));

        // Act
        executionFacade.execute(automationInfo, returnCommand);

        // Assert
        verify(delegateRunnerFactory.createInstance(automationInfo), times(1)).execute(refEq(returnCommand.getCommandDelegate()));

    }

}
