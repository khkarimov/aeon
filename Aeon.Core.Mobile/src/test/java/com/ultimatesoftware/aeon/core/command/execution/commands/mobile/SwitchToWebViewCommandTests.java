package com.ultimatesoftware.aeon.core.command.execution.commands.mobile;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IMobileDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class SwitchToWebViewCommandTests {

    @Mock
    IByWeb selector;

    @Mock
    IMobileDriver driver;

    private SwitchToWebViewCommand switchToWebViewCommand;

    @BeforeEach
    void setup() {
        this.switchToWebViewCommand = new SwitchToWebViewCommand(this.selector);
    }

    @Test
    void driverDelegate_happyPath_invokesDriverMethodAndGetterReturnsSelector() {

        //Arrange

        //Act
        this.switchToWebViewCommand.driverDelegate(this.driver);

        //Assert
        verify(this.driver, times(1)).switchToWebView(this.selector);
    }

    @Test
    void getSelector_isCalled_returnsSelector() {

        //Arrange

        //Act
        IByWeb selector = this.switchToWebViewCommand.getSelector();

        //Assert
        assertEquals(this.selector, selector);
    }
}
