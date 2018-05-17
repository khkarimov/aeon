package aeon.core.testabstraction.product;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.drivers.IDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ConfigurationTests {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private IDriver driver;

    @Mock
    private IAdapter adapter;

    private Configuration config;

    @Before
    public void setUp() {
        config = new Configuration(driver.getClass(), adapter.getClass());
    }

    @Test
    public void testGetDriver() {
        //Assert
        Assert.assertEquals(driver.getClass(), config.getDriver());
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
        Assert.assertEquals(adapter.getClass(), config.getAdapter());
    }

    @Test
    public void testSetAdapter() {
        //Act
        config.setAdapter(IAdapter.class);
        //Assert
        Assert.assertEquals(IAdapter.class, config.getAdapter());
    }

}
