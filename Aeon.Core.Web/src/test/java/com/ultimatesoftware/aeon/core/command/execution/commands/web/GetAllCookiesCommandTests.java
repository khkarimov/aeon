package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GetAllCookiesCommandTests {

    private GetAllCookiesCommand getAllCookiesCommand;

    @Mock
    private IWebDriver driver;

    @BeforeEach
    public void setup() {
        getAllCookiesCommand = new GetAllCookiesCommand();
    }

    @Test
    public void GetAllCookiesCommand() {
        // Act
        getAllCookiesCommand.commandDelegate(driver);

        // Verify
        verify(driver, times(1)).getAllCookies();
    }
}

