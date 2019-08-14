package com.ultimatesoftware.aeon.core.command.execution.commands.web;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.framework.abstraction.drivers.IWebDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WebSelectorFinderTests {

    private WebSelectorFinder webSelectorFinder;

    @Mock
    private IWebDriver driver;

    @Mock
    private IByWeb selector;

    @BeforeEach
    public void setup() {
        webSelectorFinder = new WebSelectorFinder();
    }

    @Test
    public void WebSelectorFinderFindSelector() {

        // Act
        IByWeb iBy = webSelectorFinder.findSelector(driver, selector);

        // Verify
        Assertions.assertEquals(selector, iBy);
    }
}
