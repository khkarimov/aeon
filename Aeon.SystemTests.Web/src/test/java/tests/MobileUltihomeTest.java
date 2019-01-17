package tests;

import categories.MobileTests;
import main.sample.Sample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static aeon.core.testabstraction.product.Aeon.launch;

@Category({MobileTests.class})
public class MobileUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class);
        product.browser.goToUrl("https://ultihome.ultimatesoftware.com");
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @Test
    public void ultihomeTest() {
        product.ultihome.userField.set("test username");
        product.ultihome.passField.set("test password");
        product.ultihome.loginButton.click();
    }
}
