import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserSizeMap;
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
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.TabletLandscape));
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.SmallTabletLandscape));
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.MobileLandscape));
        product.Browser.Maximize();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.UserNameTextBox.Clear();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Browser.Refresh();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.Click();
        product.Browser.GoToUrl("http://www.google.com");
        product.Browser.GoBack();
        product.Browser.GoForward();
        //scroll tests
        product.Browser.GoToUrl("http://www.tutorialspoint.com/");
        product.Browser.ScrollToEnd();
        product.Browser.ScrollToTop();
    }
}
