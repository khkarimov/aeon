package com.ultimatesoftware.aeon.core.command.execution;

import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class AutomationInfoTests {

    private AutomationInfo automationInfo;

    @Mock
    private Configuration configuration;

    @Mock
    private IDriver driver;

    @Mock
    private IAdapter adapter;


    @BeforeEach
    void setUp() {
        automationInfo = new AutomationInfo(null, null, null);
    }

    @Test
    void setAdapter_returnsCorrectly() {

        //Arrange

        //Act
        automationInfo.setAdapter(adapter);
        IAdapter automationInfoAdapter = automationInfo.getAdapter();

        //Assert
        assertEquals(automationInfoAdapter, adapter);
    }

    @Test
    void setCommandExecutionFacade_returnsCorrectly() {

        //Arrange
        CommandExecutionFacade commandExecutionFacade = new CommandExecutionFacade(null);

        //Act
        automationInfo.setCommandExecutionFacade(commandExecutionFacade);
        ICommandExecutionFacade automationInfoCommandExecutionFacade = automationInfo.getCommandExecutionFacade();

        //Assert
        assertEquals(automationInfoCommandExecutionFacade, commandExecutionFacade);
    }

    @Test
    void setConfiguration_returnsCorrectly() {

        //Arrange

        //Act
        automationInfo.setConfiguration(configuration);
        Configuration automationInfoConfiguration = automationInfo.getConfiguration();

        //Assert
        assertEquals(automationInfoConfiguration, configuration);
    }

    @Test
    void setDriver_returnsCorrectly() {

        //Arrange

        //Act
        automationInfo.setDriver(driver);
        IDriver automationInfoDriver = automationInfo.getDriver();

        //Assert
        assertEquals(automationInfoDriver, driver);
    }
}
