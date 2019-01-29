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

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        p.put("test", "Default");
        p.setProperty("test", "Success");

        // Act
        config.setProperties(p);

        // Assert
        String actual = config.getString("test", "Default");
        assertEquals("Success", actual);
    }

    @Test
    public void getConfigurationFields_happyPath_returnsConfigurationFields() throws IllegalAccessException {

        // Arrange

        // Act
        List<Field> keys = config.getConfigurationFields();
        List<Field> nonSynthetic = new ArrayList<>();

        for (Field field : keys) {
            if (!field.isSynthetic()) {
                nonSynthetic.add(field);
                nonSynthetic.get(nonSynthetic.size() - 1).setAccessible(true);
            }
        }

        // Assert
        assertEquals("aeon.timeout", keys.get(0).get(config));
        assertEquals("aeon.throttle", keys.get(1).get(config));
        assertEquals("aeon.implicit_reporting", keys.get(2).get(config));
    }
}
