package aeon.core.command.execution.commands.web;


import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertEquals;

public class WebSelectorFinderTests {

    private WebSelectorFinder webSelectorFinder;

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @Before
    public void setup(){webSelectorFinder = new WebSelectorFinder();}

    @Test(expected = IllegalArgumentException.class)
    public void DriverNullThrowsException(){

        // Act
        webSelectorFinder.findSelector(null, selector);

        // Assert
        thrown.expectMessage("driver");
    }

    @Test
    public void WebSelectorFinderFindSelector(){

        // Act
        IByWeb iBy = webSelectorFinder.findSelector(driver, selector);

        // Verify
        assertEquals(selector, iBy);
    }
}
