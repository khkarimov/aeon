package com.ultimatesoftware.aeon.extensions.selenium;

import com.ultimatesoftware.aeon.core.common.Capabilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeleniumAdapterFactoryTests {

    private SeleniumAdapterFactory seleniumAdapterFactory;

    @BeforeEach
    void setup() {
        this.seleniumAdapterFactory = new SeleniumAdapterFactory();
    }

    @Test
    void getProvidedCapability() {

        // Arrange

        // Act
        Capabilities providedCapability = this.seleniumAdapterFactory.getProvidedCapability();

        // Assert
        assertEquals(Capabilities.WEB, providedCapability);
    }
}
