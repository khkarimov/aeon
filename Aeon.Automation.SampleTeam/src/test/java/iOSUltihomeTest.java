import aeon.core.common.web.AppRuntime;
import main.Sample;
import org.junit.*;

import static aeon.core.testabstraction.product.Aeon.launch;

public class iOSUltihomeTest {
    private static Sample product;

    @Before
    public void beforeTests() {
        product = launch(Sample.class, AppRuntime.IOSSafari);
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