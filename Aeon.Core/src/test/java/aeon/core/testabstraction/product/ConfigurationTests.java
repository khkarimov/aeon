package aeon.core.testabstraction.product;

import aeon.core.common.AeonConfigKey;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Properties;

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
    public void getDriverSetDriver_happyPath_returnsDriverClass() {

        // Arrange

        // Act
        config.setDriver(IDriver.class);

        // Assert
        assertEquals(config.getDriver(), IDriver.class);
    }

    @Test
    public void getAdapterSetAdapter_happyPath_returnsAdapterClass() {

        // Arrange

        // Act
        config.setAdapter(IAdapter.class);

        // Assert
        assertEquals(config.getAdapter(), IAdapter.class);
    }

    @Test
    public void setProperties_happyPath_propertiesChanged() {

        // Arrange
        Properties p = new Properties();
        p.put(Configuration.Keys.TIMEOUT, "Default");
        p.setProperty(Configuration.Keys.TIMEOUT.getKey(), "Success");

        // Act
        config.setProperties(p);

        // Assert
        String actual = config.getString(Configuration.Keys.TIMEOUT, "Default");
        assertEquals("Success", actual);
    }

    @Test
    public void getConfigurationFields_happyPath_returnsConfigurationFields() throws IllegalAccessException {

        // Arrange

        // Act
        List<AeonConfigKey> keys = config.getConfigurationFields();

        // Assert
        assertEquals("aeon.timeout", keys.get(0).getKey());
        assertEquals("aeon.throttle", keys.get(1).getKey());
        assertEquals("aeon.implicit_reporting", keys.get(2).getKey());
    }
}
