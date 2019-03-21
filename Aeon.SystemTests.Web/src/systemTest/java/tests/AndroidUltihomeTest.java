package tests;

import categories.AndroidTests;
import main.sample.Sample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static com.ultimatesoftware.aeon.core.testabstraction.product.Aeon.launch;

@Category({AndroidTests.class})
public class AndroidUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class);
        product.browser.goToUrl("https://my.ultimatesoftware.com/mobile");
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
