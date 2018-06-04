package tests;

import main.sample.Sample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import static aeon.core.testabstraction.product.Aeon.launch;

@Tag("IOSTests")
public class iOSUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class);
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