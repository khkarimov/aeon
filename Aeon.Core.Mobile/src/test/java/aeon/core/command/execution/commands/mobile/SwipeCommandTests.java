package aeon.core.command.execution.commands.mobile;

import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.function.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SwipeCommandTests {

    @Mock
    private IMobileDriver driver;

    private SwipeCommand command;

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    void swipeCommand_trueTrue_swipeHorizontallyLeftOrDown(boolean horizontal, boolean leftOrDown) {

        //Arrange
        command = new SwipeCommand(horizontal, leftOrDown);

        //Act
        Consumer<IDriver> action = command.getCommandDelegate();
        action.accept(driver);

        //Assert
        verify(driver, times(1)).swipe(horizontal, leftOrDown);

    }
}
