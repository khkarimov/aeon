package tests;

import main.sample.Sample;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

@Tag("categories.AndroidTests")
public class AndroidUltihomeTest {
    private static Sample product;

    @BeforeEach
    public void beforeTests() {
        product = launch(Sample.class);
        product.browser.goToUrl("http://ultihome.ultimatesoftware.com");
    }

    @AfterEach
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