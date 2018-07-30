package aeon.core.testabstraction.product;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ConfigurationTests {

    @Mock
    private IDriver driver;

    @Mock
    private IAdapter adapter;

    private Configuration config;

    @BeforeEach
    public void setUp() {
        config = new Configuration(driver.getClass(), adapter.getClass());
    }

    @Test
    public void testGetDriver() {
        // Assert
        assertEquals(config.getDriver(), driver.getClass());
    }

    @Test
    public void testSetDriver(){
        // Act
        config.setDriver(IDriver.class);
        // Assert
        assertEquals(config.getDriver(), IDriver.class);
    }

    @Test
    public void testGetAdapter() {
        // Assert
        assertEquals(config.getAdapter(), adapter.getClass());
    }

    @Test
    public void testSetAdapter() {
        // Act
        config.setAdapter(IAdapter.class);
        // Assert
        assertEquals(config.getAdapter(), IAdapter.class);
    }

}
