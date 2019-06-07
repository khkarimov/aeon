package com.ultimatesoftware.aeon.core.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ResourcesTests {

    @Test
    void getString_keyExists_returnsValue() {

        // Arrange

        // Act
        String value = Resources.getString("NoSuchElementException_ctor_SpecificMessage");

        // Assert
        assertEquals("The specified element with css selector '%1$s' does not exist.", value);
    }

    @Test
    void getString_keyDoesNotExist_returnsKey() {

        // Arrange

        // Act
        String value = Resources.getString("SomeNonExistentKey");

        // Assert
        assertEquals("SomeNonExistentKey", value);
    }
}
