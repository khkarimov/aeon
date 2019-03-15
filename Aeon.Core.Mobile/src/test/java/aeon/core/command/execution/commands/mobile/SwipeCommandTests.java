package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class SwipeCommandTests {

    @Mock
    private IMobileDriver driver;

    private SwipeCommand command;

    private boolean horizontal = true;

    private boolean leftOrDown = true;

    @BeforeEach
    public void setUp() {
        command = new SwipeCommand(horizontal, leftOrDown);
    }

    @Test
    public void swipeCommand_true_swipeHorizontallyLeftOrDown() {
        //Arrange

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(true, true);
    }
}
