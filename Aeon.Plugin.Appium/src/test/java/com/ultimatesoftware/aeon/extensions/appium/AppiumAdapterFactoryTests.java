package com.ultimatesoftware.aeon.extensions.appium;

import com.ultimatesoftware.aeon.core.common.Capabilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppiumAdapterFactoryTests {

    private AppiumAdapterFactory appiumAdapterFactory;

    @BeforeEach
    void setup() {
        this.appiumAdapterFactory = new AppiumAdapterFactory();
    }

    @Test
    void getProvidedCapability() {

        // Arrange

        // Act
        Capabilities providedCapability = this.appiumAdapterFactory.getProvidedCapability();

        // Assert
        assertEquals(Capabilities.MOBILE, providedCapability);
    }
}
