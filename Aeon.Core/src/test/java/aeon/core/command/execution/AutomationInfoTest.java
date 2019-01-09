package aeon.core.command.execution;

import aeon.core.command.execution.consumers.interfaces.IDelegateRunnerFactory;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AutomationInfoTest {

    private AutomationInfo automationInfo;

    @Mock
    private Configuration config;

    @Mock
    private IDriver driver;

    @Mock
    private IAdapter adapter;

    @BeforeEach
    void setUp() {
        automationInfo = new AutomationInfo(config, driver, adapter);
    }

    @Test
    void getAdapter() {

        //Arrange

        //Act

        //Assert
        assertEquals(automationInfo.getAdapter(), adapter);
    }

    @Test
    void setAdapter() {

        //Arrange
        IAdapter adapter = mock(IAdapter.class);

        //Act
        automationInfo.setAdapter(adapter);

        //Assert
        assertEquals(automationInfo.getAdapter(), adapter);
    }

    @Test
    void getCommandExecutionFacade() {

        //Arrange

        //Act

        //Assert
        assertNull(automationInfo.getCommandExecutionFacade());
    }

    @Test
    void setCommandExecutionFacade() {

        //Arrange
        CommandExecutionFacade commandExecutionFacade = new CommandExecutionFacade(mock(IDelegateRunnerFactory.class));

        //Act
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        //Assert
        assertEquals(automationInfo.getCommandExecutionFacade(), commandExecutionFacade);
    }

    @Test
    void getConfiguration() {

        //Arrange

        //Act

        //Assert
        assertEquals(automationInfo.getConfiguration(), config);
    }

    @Test
    void setConfiguration() {

        //Arrange
        Configuration configuration = mock(Configuration.class);

        //Act
        automationInfo.setConfiguration(configuration);

        //Assert
        assertEquals(automationInfo.getConfiguration(), configuration);
    }

    @Test
    void getDriver() {

        //Arrange

        //Act

        //Assert
        assertEquals(automationInfo.getDriver(), driver);
    }

    @Test
    void setDriver() {

        //Arrange
        IDriver driver = mock(IDriver.class);

        //Act
        automationInfo.setDriver(driver);

        //Assert
        assertEquals(automationInfo.getDriver(), driver);
    }
}