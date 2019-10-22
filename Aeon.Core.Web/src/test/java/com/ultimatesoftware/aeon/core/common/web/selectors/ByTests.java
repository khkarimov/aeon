package com.ultimatesoftware.aeon.core.common.web.selectors;

import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ByTests {

    // Variables
    private By by;

    @Test
    void cssSelector_whenSelectorPassed_returnsProperBy() {

        // Arrange

        // Act
        By returned = By.cssSelector("selector");

        // Assert
        assertEquals("selector", returned.toString());
    }

    @Test
    void dataAutomationAttribute_whenInputPassed_returnsProperBy() {

        // Arrange

        // Act
        By returned = By.dataAutomationAttribute("input");

        // Assert
        assertEquals("[data-automation=\"input\"]", returned.toString());
    }

    @Test
    void da_whenInputPassed_returnsProperBy() {

        // Arrange

        // Act
        By returned = By.da("input");

        // Assert
        assertEquals("[da=\"input\"]", returned.toString());
    }

    @Test
    void jQuery_whenSelectorPassed_returnsProperByJQuery() {

        // Arrange

        // Act
        ByJQuery returned = By.jQuery("selector");

        // Assert
        assertEquals("$(\"selector\")", returned.toString());
    }

    @Test
    void toJQuery_whenCalled_returnsProperByJQuery() {

        // Arrange
        By by = By.cssSelector("selector");

        // Act
        ByJQuery returned = by.toJQuery();

        // Assert
        assertEquals("$(\"selector\")", returned.toString());
    }

    @Test
    void find_whenBySelectorPassed_returnsProperIByWeb() {

        // Arrange
        By by = By.cssSelector("selector");

        // Act
        IByWeb returned = by.find(by);

        // Assert
        assertEquals("selector selector", returned.toString());
    }

    @Test
    void find_whenByJQuerySelectorPassed_returnsPropperIByWeb() {

        // Arrange
        By by = By.cssSelector("selector");
        IByWeb byJQuery = new ByJQuery("selector");

        // Act
        IByWeb returned = by.find(byJQuery);

        // Assert
        assertEquals("$(\"selector\").find($(\"selector\"))", returned.toString());
    }
}
