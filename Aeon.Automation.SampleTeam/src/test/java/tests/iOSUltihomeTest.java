package tests;

import aeon.core.common.web.BrowserType;
import categories.IOSTests;
import main.sample.Sample;
import org.junit.*;
import org.junit.experimental.categories.Category;

import static aeon.core.testabstraction.product.Aeon.launch;

@Category({IOSTests.class})
public class iOSUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.IOSSafari);
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