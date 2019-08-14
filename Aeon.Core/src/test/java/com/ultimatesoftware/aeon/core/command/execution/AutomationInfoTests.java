package com.ultimatesoftware.aeon.core.command.execution;

import com.ultimatesoftware.aeon.core.extensions.ITestExecutionExtension;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IDriver;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.pf4j.PluginManager;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

    @Mock
    private CommandExecutionFacade commandExecutionFacade;

    @Mock
    private PluginManager pluginManager;

    @Mock
    private ITestExecutionExtension testExecutionExtension1;

    @Mock
    private ITestExecutionExtension testExecutionExtension2;

    @BeforeEach
    void setUp() {
        when(this.pluginManager.getExtensions(ITestExecutionExtension.class))
                .thenReturn(Arrays.asList(this.testExecutionExtension1, this.testExecutionExtension2));
        Aeon.setPluginManager(this.pluginManager);
        this.automationInfo = new AutomationInfo(this.configuration, this.driver, this.adapter);
    }

    @Test
    void constructor_whenInitialized_triggersOnStartUpEvent() {

        // Arrange

        // Act

        // Assert
        verify(this.testExecutionExtension1, times(1)).onStartUp(eq(this.configuration), any(String.class));
        verify(this.testExecutionExtension2, times(1)).onStartUp(eq(this.configuration), any(String.class));
    }

    @Test
    void constructor_setsConfiguration_returnsCorrectly() {

        //Arrange

        //Act
        Configuration automationInfoConfiguration = this.automationInfo.getConfiguration();

        //Assert
        assertEquals(automationInfoConfiguration, this.configuration);
    }

    @Test
    void constructor_setDriver_returnsCorrectly() {

        //Arrange

        //Act
        IDriver automationInfoDriver = this.automationInfo.getDriver();

        //Assert
        assertEquals(automationInfoDriver, this.driver);
    }

    @Test
    void setCommandExecutionFacade_returnsCorrectly() {

        //Arrange

        //Act
        this.automationInfo.setCommandExecutionFacade(this.commandExecutionFacade);
        ICommandExecutionFacade automationInfoCommandExecutionFacade = this.automationInfo.getCommandExecutionFacade();

        //Assert
        assertEquals(automationInfoCommandExecutionFacade, this.commandExecutionFacade);
    }

    @Test
    void launched_whenCalled_triggersOnAfterLaunchEvent() {

        // Arrange

        // Act
        this.automationInfo.launched();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onAfterLaunch(this.configuration, this.adapter);
        verify(this.testExecutionExtension2, times(1)).onAfterLaunch(this.configuration, this.adapter);
    }

    @Test
    void testSucceeded_isCalled_triggersTestOnSucceededTestEvent() {

        // Arrange

        // Act
        this.automationInfo.testSucceeded();

        // Assert
        verify(this.testExecutionExtension1, times(1)).onSucceededTest();
        verify(this.testExecutionExtension2, times(1)).onSucceededTest();
    }

    @Test
    void testFailed_calledWithNoException_triggersOnFailedTestEvent() {

        // Arrange

        // Act
        this.automationInfo.testFailed("error-message");

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("error-message", null);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("error-message", null);
    }

    @Test
    void testFailed_calledWithException_triggersOnFailedTestEvent() {

        // Arrange
        Exception exception = new Exception("exception-message");

        // Act
        this.automationInfo.testFailed("error-message", exception);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onFailedTest("error-message", exception);
        verify(this.testExecutionExtension2, times(1)).onFailedTest("error-message", exception);
    }

    @Test
    void screenshotTaken_isCalled_triggersOnExecutionEventEvent() {

        // Arrange
        Image image = mock(Image.class);

        // Act
        this.automationInfo.screenshotTaken(image);

        // Assert
        verify(this.testExecutionExtension1, times(1)).onExecutionEvent("screenshotTaken", image);
        verify(this.testExecutionExtension2, times(1)).onExecutionEvent("screenshotTaken", image);
    }
}
