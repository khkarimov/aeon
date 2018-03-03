package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import categories.UbuntuTests;
import categories.WindowsTests;
import main.sample.Sample;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static aeon.core.testabstraction.product.Aeon.launch;

@Category({WindowsTests.class, UbuntuTests.class})
public class SampleBaseTest {

    public static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeTests() {
        product = launch(Sample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @AfterClass
    public static void afterClass() {
        Aeon.done();
    }
}
