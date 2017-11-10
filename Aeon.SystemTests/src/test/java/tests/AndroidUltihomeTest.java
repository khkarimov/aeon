package tests;

import aeon.core.common.web.BrowserType;
import categories.AndroidTests;
import main.sample.Sample;
import org.junit.*;
import org.junit.experimental.categories.Category;
import static aeon.core.testabstraction.product.Aeon.launch;

@Category({AndroidTests.class})
public class AndroidUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.AndroidChrome);
        product.browser.goToUrl("http://ultihome.ultimatesoftware.com");
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @Test
    public void ultihomeTest(){
        product.ultihome.userField.set("test username");
        product.ultihome.passField.set("test password");
        product.ultihome.loginButton.click();
    }
}