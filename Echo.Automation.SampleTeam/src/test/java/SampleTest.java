import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserSizeMap;
import echo.selenium.SeleniumCookie;
import main.Sample;
import org.junit.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static echo.core.common.web.BrowserType.Firefox;
import static echo.core.test_abstraction.product.Echo.Launch;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SampleTest {

    private static Sample product;

    @Before
    public static void fixtureSetUp() {
        product = Launch(Sample.class, Firefox);
        product.Browser.GoToUrl("http://gandaras01web.newgen.corp/");
    }

    @After
    public static void fixtureTearDown() {
        product.Browser.Quit();
    }

    @Test
    public void TestGoBackGoForward_01() {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.Click();
        product.Browser.GoBack();
        product.Browser.GoForward();
    }

    @Test
    public void TestClearAndRefresh_02()
    {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.UserNameTextBox.Clear();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Browser.Refresh();
    }

    @Test
    public void TestWindowResizing_03(){
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.Click();
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.TabletLandscape));
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.SmallTabletLandscape));
        product.Browser.Resize(BrowserSizeMap.Map(BrowserSize.MobileLandscape));
        product.Browser.Maximize();

    }

    @Test
    public void TestDoubleClickScrollTopEnd_04()
    {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.DoubleClick();
        product.Browser.GoToUrl("http://www.tutorialspoint.com");
        product.Browser.
    }
    @Test
    public void SampleTest2()
    {
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
        product.Login.LoginButton.DoubleClick();
        //product.Login.LoginButton.Click();
        //product.Browser.GoToUrl("http://www.google.com");
        product.Browser.GoBack();
        product.Browser.GoForward();
    }
}
