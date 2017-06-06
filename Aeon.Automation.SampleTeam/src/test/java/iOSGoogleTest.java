import aeon.core.common.web.BrowserType;
import main.Sample;
import org.junit.*;

import static aeon.core.testabstraction.product.Aeon.launch;

public class iOSGoogleTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.iOSSafari);
        product.browser.goToUrl("http://www.google.com");
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @Test
    public void googleTest(){
        product.google.formTextBox.set("let me google that for you\n");
    }
}