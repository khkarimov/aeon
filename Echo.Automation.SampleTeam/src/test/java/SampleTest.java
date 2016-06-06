import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserSizeMap;
import echo.core.common.web.WebSelectOption;
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
    public void SetUp() {
        product = Launch(Sample.class, Firefox);
        product.Browser.GoToUrl("http://gandaras01web.newgen.corp/");
    }

    @After
    public void TearDown() {
        product.Browser.Quit();
    }

    @Test
    public void TestGoBackGoForward_01() {
        String [] texts = {"English (USA)", "Italiano (IT)"};
        String [] shouldfail = {"Klingon", "African Clicky Noises", "Reptilian Hissing"};
        String [] values = {"1", "2"};
        String [] valuesShouldFail = {"-12", "Blue"};
        product.Login.LanguageSelect.Click();
        product.Login.LanguageSelect.HasOptions(texts, null,  WebSelectOption.Text);
        product.Login.LanguageSelect.HasOptions(values, null,  WebSelectOption.Value);
        product.Login.LanguageSelect.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
        product.Login.LanguageSelect.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
        product.Login.LoginButton.Click();
        product.Browser.GoBack();
        product.Browser.GoForward();
        //product.Home.ViewPayStatement.Click();
        //System.out.println("After Click");
    }

    @Test
    public void TestClearAndRefresh_02() {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.Click();
        product.Browser.Refresh();
        product.Browser.ClearBrowserStorage();
    }

    @Test
    public void TestWindowResizing_03() {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.Click();
        product.Browser.Resize(BrowserSize.TabletLandscape);
        product.Browser.Resize(BrowserSize.SmallTabletLandscape);
        product.Browser.Resize(BrowserSize.MobileLandscape);
        product.Browser.Maximize();

    }

    @Test
    public void TestDoubleClickScrollTopEnd_04() {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.DoubleClick();
        product.Browser.GoToUrl("http://www.tutorialspoint.com");
        product.Browser.ScrollToEnd();
        product.Browser.ScrollToTop();
    }

    @Test
    public void SampleTest2() {
        product.Browser.Resize(BrowserSize.TabletLandscape);
        product.Browser.Resize(BrowserSize.SmallTabletLandscape);
        product.Browser.Resize(BrowserSize.MobileLandscape);
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

    @Test
    public void TestGetElementAttributeWithTextBoxNameAttribute(){
        String testAttributeValues = product.Login.UserNameTextBox.GetElementAttribute("name").toString();
        System.out.println(testAttributeValues);
    }

}
