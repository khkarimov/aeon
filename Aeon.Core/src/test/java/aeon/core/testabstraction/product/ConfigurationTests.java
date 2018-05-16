package aeon.core.testabstraction.product;

import aeon.core.common.web.BrowserType;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ConfigurationTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

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
        //Assert
        Assertions.assertEquals(driver.getClass(), config.getDriver());
    }

    @Test
    public void testSetDriver(){
        //Act
        config.setDriver(IDriver.class);
        //Assert
        assert(IDriver.class.equals(config.getDriver()));
    }

    @Test
    public void testGetAdapter() {
        //Assert
        Assertions.assertEquals(adapter.getClass(), config.getAdapter());
    }

    @Test
    public void testSetAdapter() {
        //Act
        config.setAdapter(IAdapter.class);
        //Assert
        Assertions.assertEquals(IAdapter.class, config.getAdapter());
    }

    @Test
    public void testGetBrowserType()  {
        //Act
        config.setBrowserType(BrowserType.Chrome);
        //Assert
        assert(BrowserType.Chrome).equals(config.getBrowserType());
    }

    @Test
    public void testSetBrowserType()  {
        //Act
        config.setBrowserType(BrowserType.Firefox);
        //Assert
        assert(BrowserType.Firefox).equals(config.getBrowserType());
    }
}
