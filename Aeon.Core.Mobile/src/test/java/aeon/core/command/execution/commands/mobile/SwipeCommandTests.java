package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
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

    private boolean horizontal;

    private boolean leftOrDown;

    @Test
    public void swipeCommand_trueTrue_swipeHorizontallyLeftOrDown() {

        //Arrange
        horizontal = true;
        leftOrDown = true;
        command = new SwipeCommand(horizontal, leftOrDown);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(true, true);

    }

    @Test
    public void swipeCommand_falseTrue_swipeHorizontallyLeftOrDown() {

        //Arrange
        horizontal = false;
        leftOrDown = true;
        command = new SwipeCommand(horizontal, leftOrDown);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(false, true);

    }

    @Test
    public void swipeCommand_trueFalse_swipeHorizontallyLeftOrDown() {

        //Arrange
        horizontal = true;
        leftOrDown = false;
        command = new SwipeCommand(horizontal, leftOrDown);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(true, false);

    }

    @Test
    public void swipeCommand_falseFalse_swipeHorizontallyLeftOrDown() {

        //Arrange
        horizontal = false;
        leftOrDown = false;
        command = new SwipeCommand(horizontal, leftOrDown);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(false, false);

    }

}
