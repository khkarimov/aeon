package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import static aeon.core.testabstraction.product.Aeon.launch;

@Tag("WindowsTests")
@Tag("UbuntuTests")
public class SampleBaseTest {

    public static Sample product;

    @BeforeEach
    public void beforeTests() {
        product = launch(Sample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @AfterEach
    public void afterTests() {
        product.browser.quit();
    }

    @AfterAll
    public static void afterClass() {
        Aeon.done();
    }
}
