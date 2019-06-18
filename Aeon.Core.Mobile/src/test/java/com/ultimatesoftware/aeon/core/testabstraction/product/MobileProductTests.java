package com.ultimatesoftware.aeon.core.testabstraction.product;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.Capabilities;
import com.ultimatesoftware.aeon.core.common.Capability;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class MobileProductTests {

    @Mock
    private AutomationInfo automationInfo;

    @Test
    void testIsAnnotatedWithMobileCapability() {

        // Arrange

        // Act

        // Assert
        assertEquals(Capabilities.MOBILE, MobileProduct.class.getAnnotation(Capability.class).value());
    }

    @Test
    void construct_isInstantiated_createsMobileDevice() {

        // Arrange

        // Act
        MobileProduct mobileProduct = new MobileProduct(this.automationInfo);

        // Assert
        assertNotNull(mobileProduct.mobileDevice);
    }
}
