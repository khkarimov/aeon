package aeon.core.command.execution;

import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class CommandExecutionFacadeTests {


    @Mock
    private AutomationInfo automationInfo;

    @Mock
    private Command command;

    @Mock
    private IDelegateRunnerFactory delegateRunnerFactory;

    private CommandExecutionFacade executionFacade;

    @BeforeEach
    public void setup() {
        executionFacade = new CommandExecutionFacade(this.delegateRunnerFactory);
    }

    @Test
    public void testExecuteNullCommand() {
        //arrange
        command = null;
        //act
        assertThrows(IllegalArgumentException.class, () -> executionFacade.execute(automationInfo, command));
        //assert
    }

    @Test
    public void testExecuteCommand() {

    }

    @Test
    public void testExecuteObject() {

    }

}
