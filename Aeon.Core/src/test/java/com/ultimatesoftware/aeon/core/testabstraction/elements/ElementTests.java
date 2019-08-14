package com.ultimatesoftware.aeon.core.testabstraction.elements;

import com.ultimatesoftware.aeon.core.common.interfaces.IBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ElementTests {

    @Mock
    private IBy selector;

    static class ElementStub extends Element {
        IBy selectorStub;

        ElementStub(IBy selector) {
            super(selector);
            this.selectorStub = selector;
        }
    }

    @Test
    void constructor_setsSelector() {

        // Arrange

        // Act
        ElementStub page = new ElementStub(this.selector);

        // Assert
        assertEquals(selector, page.selectorStub);
    }

    @Test
    void constructor_nullSelector_throwsException() {

        // Arrange
        this.selector = null;

        // Act
        Executable action = () -> new Element(this.selector);

        // Assert
        Exception exception = assertThrows(IllegalArgumentException.class, action);
        assertEquals("An element has to have a valid selector", exception.getMessage());
    }
}
