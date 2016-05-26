import echo.selenium.SeleniumCookie;
import main.Sample;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static echo.core.common.web.BrowserType.Firefox;
import static echo.core.test_abstraction.product.Echo.Launch;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SampleTest {

    private static Sample product;

    @BeforeClass
    public static void fixtureSetUp() {
        product = Launch(Sample.class, Firefox);
        product.Browser.GoToUrl("http://gandaras01web.newgen.corp/");
    }

    @AfterClass
    public static void fixtureTearDown() {
        product.Browser.Quit();
    }

    @Test
    public void SampleTest() {
        product.Browser.Maximize();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.DoubleClick();
         }
}
