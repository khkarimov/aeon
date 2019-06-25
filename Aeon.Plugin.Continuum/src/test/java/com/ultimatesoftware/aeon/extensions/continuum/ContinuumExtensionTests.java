package com.ultimatesoftware.aeon.extensions.continuum;

import com.levelaccess.continuum.AccessibilityConcern;
import com.levelaccess.continuum.Continuum;
import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.core.framework.abstraction.adapters.IAdapter;
import com.ultimatesoftware.aeon.core.testabstraction.product.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ContinuumExtensionTests {

    @Mock
    private IConfiguration configuration;

    @Mock
    private Configuration aeonConfiguration;

    @Mock
    private Continuum continuum;

    @Mock
    private RemoteWebDriver webDriver;

    @Mock
    private IAdapter adapter;

    @Mock
    private MutableCapabilities mutableCapabilities;

    @Mock
    private Exception exception;

    @Mock
    private Logger log;

    private ContinuumExtension continuumExtension;

    @BeforeEach
    void setUp() {
        this.continuumExtension = new ContinuumExtension(this.configuration, this.continuum);
        ContinuumExtension.log = this.log;
    }

    @Test
    void createInstance_createsAnInstanceSuccessfully() {

        // Arrange

        // Act
        Object extension = ContinuumExtension.createInstance();

        // Assert
        assertEquals(ContinuumExtension.class, extension.getClass());
    }

    @Test
    void onGenerateCapabilities_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onGenerateCapabilities(this.aeonConfiguration, this.mutableCapabilities);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.mutableCapabilities);
    }

    @Test
    void onAfterLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onAfterLaunch(this.aeonConfiguration, this.webDriver);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.webDriver);
    }

    @Test
    void onStartUp_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onStartUp(this.aeonConfiguration, "correlationId");

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onBeforeStart_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onBeforeStart("correlationId", "suiteName");

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onBeforeLaunch_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onBeforeLaunch(this.aeonConfiguration);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
    }

    @Test
    void onAfterLaunch_isCalledWithAdapter_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onAfterLaunch(this.aeonConfiguration, this.adapter);

        // Assert
        verifyZeroInteractions(this.aeonConfiguration);
        verifyZeroInteractions(this.adapter);
    }

    @Test
    void onBeforeTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onBeforeTest("name");

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onSucceededTest_noAccessibilityErrors_doesNotThrowException() {

        // Arrange
        when(this.continuum.getAccessibilityConcerns()).thenReturn(new ArrayList<>());

        // Act
        Executable action = () -> this.continuumExtension.onSucceededTest();

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    void onSucceededTest_accessibilityErrors_throwsException() {

        // Arrange
        ArrayList<AccessibilityConcern> errors = new ArrayList<>();
        errors.add(new AccessibilityConcern());
        errors.add(new AccessibilityConcern());
        when(this.continuum.getAccessibilityConcerns()).thenReturn(errors);

        // Act
        Executable action = () -> this.continuumExtension.onSucceededTest();

        // Assert
        assertThrows(ContinuumAccessibilityException.class, action);
    }

    @Test
    void onSkippedTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onSkippedTest("name");

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onFailedTest_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onFailedTest("reason", this.exception);

        // Assert
        verifyZeroInteractions(this.exception);
    }

    @Test
    void onBeforeStep_implicitDisabled_doesNotDoAnything() {

        // Arrange
        when(this.configuration.getBoolean(ContinuumConfiguration.Keys.IMPLICIT_VALIDATIONS, true)).thenReturn(false);

        // Act
        this.continuumExtension.onBeforeStep("THEN the element is visible");

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onBeforeStep_implicitEnabledAndItIsThenStep_runsAccessibilityTests() {

        // Arrange
        when(this.configuration.getBoolean(ContinuumConfiguration.Keys.IMPLICIT_VALIDATIONS, true)).thenReturn(true);
        this.continuumExtension.onAfterLaunch(this.aeonConfiguration, this.webDriver);

        // Act
        this.continuumExtension.onBeforeStep("THEN the element is visible");

        // Assert
        verify(this.continuum, times(1)).setUp(this.webDriver);
        verify(this.continuum, times(1)).runAllTests();
    }

    @Test
    void onBeforeStep_implicitEnabledAndItIsNotThenStep_doesNotDoAnything() {

        // Arrange
        when(this.configuration.getBoolean(ContinuumConfiguration.Keys.IMPLICIT_VALIDATIONS, true)).thenReturn(true);

        // Act
        this.continuumExtension.onBeforeStep("When I log in");

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onDone_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onDone();

        // Assert
        verifyZeroInteractions(this.continuum);
    }

    @Test
    void onExecutionEvent_isCalled_doesNotDoAnything() {

        // Arrange

        // Act
        this.continuumExtension.onExecutionEvent("executionEvent", this.webDriver);

        // Assert
        verifyZeroInteractions(this.webDriver);
    }

    @Test
    void runAccessibilityTests_isCalled_runsAccessibilityTests() {

        // Arrange
        this.continuumExtension.onAfterLaunch(this.aeonConfiguration, this.webDriver);

        // Act
        this.continuumExtension.runAccessibilityTests("pageName");

        // Assert
        verify(this.continuum, times(1)).setUp(this.webDriver);
        verify(this.continuum, times(1)).runAllTests();
    }
}
