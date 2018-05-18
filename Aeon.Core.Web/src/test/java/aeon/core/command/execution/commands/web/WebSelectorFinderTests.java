package aeon.core.command.execution.commands.web;

import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WebSelectorFinderTests {

    private WebSelectorFinder webSelectorFinder;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @BeforeEach
    public void setup(){webSelectorFinder = new WebSelectorFinder();}

    @Test
    public void DriverNullThrowsException(){
        // Arrange
        Exception illegalArgumentException;

        // Act
        illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> webSelectorFinder.findSelector(null, selector));

        // Assert
        Assertions.assertEquals("driver", illegalArgumentException.getMessage());
    }

    @Test
    public void WebSelectorFinderFindSelector(){

        // Act
        IByWeb iBy = webSelectorFinder.findSelector(driver, selector);

        // Verify
        assertEquals(selector, iBy);
    }
}
