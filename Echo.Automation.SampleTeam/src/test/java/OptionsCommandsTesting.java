import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import main.Sample;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static echo.core.test_abstraction.product.Echo.Launch;

@Ignore
public class OptionsCommandsTesting {
    private static Sample product;

    @Before
    public void SetUp() {
        product = Launch(Sample.class, BrowserType.Chrome);
        product.browser.Maximize();
        product.browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void TearDown() {
        product.browser.Quit();
    }

    @Test
    public void TestHasOptions_ListHasOptions_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestHasOptions_ListHasOptions_ByText() {
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void TestDoesNotHaveOptions_Success_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestDoesNotHaveOptions_Success_ByText() {
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void TestClearAndRefresh_02() {
        product.StartPage.AlertTitleTextBox.Set("Alert Title");
        product.browser.Refresh();
        product.StartPage.AlertTitleTextBox.Clear();
    }

    @Test
    public void TestWindowResizing_03() {
        product.browser.Resize(BrowserSize.TabletLandscape);
        product.browser.Resize(BrowserSize.SmallTabletLandscape);
        product.browser.Resize(BrowserSize.MobileLandscape);
        product.browser.Maximize();
    }

    @Test
    public void TestScrollToTopAndScrollToEnd_04() throws InterruptedException {
        product.browser.GoToUrl("http://www.tutorialspoint.com");
        product.browser.ScrollToEnd();
        product.browser.ScrollToTop();
    }

    //The Following Test Methods are dependent on an UltiPro Environment, and have not been converted to use our Sample Page.


//    @Test
//    public void TestVisibleAndNotVisible_05() {
//        product.Login.WarningMessage.NotVisible();
//        product.Login.UserNameTextBox.Set("usa-canu");
//        product.Login.PasswordTextBox.Set("FAIL");
//        product.Login.LoginButton.Click();
//        product.Login.WarningMessage.Visible();
//    }
//
//    @Test
//    public void SampleTest2() {
//        product.browser.Resize(BrowserSize.TabletLandscape);
//        product.browser.Resize(BrowserSize.SmallTabletLandscape);
//        product.browser.Resize(BrowserSize.MobileLandscape);
//        product.browser.Maximize();
//        product.Login.UserNameTextBox.Set("usa-canu");
//        product.Login.UserNameTextBox.Clear();
//        product.Login.UserNameTextBox.Set("usa-canu");
//        product.Login.PasswordTextBox.Set("password");
//        product.browser.Refresh();
//        product.Login.UserNameTextBox.Set("usa-canu");
//        product.Login.PasswordTextBox.Set("password");
//        product.Login.LoginButton.DoubleClick();
//        //product.Login.LoginButton.Click();
//        //product.browser.GoToUrl("http://www.google.com");
//        product.browser.GoBack();
//        product.browser.GoForward();
//    }
//
//    @Test
//    public void TestGetElementAttributeWithTextBoxNameAttribute() {
//        String testAttributeValues = product.Login.UserNameTextBox.GetElementAttribute("name").toString();
//        System.out.println("User Name text box name value: " + testAttributeValues);
//    }
//
//    @Test
//    public void TestHasElementsInOrder() {
//        String[] options = new String[]{"English (USA)", "Italiano (ITA)", "Melayu (MYS)"};
//        String[] values = new String[]{"0", "5", "13"};
//        String[] badValues = new String[]{"0", "13", "5"};
//        String[] badOptions = new String[]{"English (USA)", "Melayu (MYS)", "Italiano (ITA)"};
//        String[] value = new String[]{"0"};
//        String[] option = new String[]{"English (USA)"};
//        product.Login.LanguageSelect.HasOptionsInOrder(options, WebSelectOption.Text);
//        product.Login.LanguageSelect.HasOptionsInOrder(values, WebSelectOption.Value);
//        product.Login.LanguageSelect.HasOptionsInOrder(value, WebSelectOption.Value);
//        product.Login.LanguageSelect.HasOptionsInOrder(option, WebSelectOption.Text);
//    }
//
//    @Test
//    public void TestHas() {
//        String[] texts = {"English (USA)", "English (GBR)", "English (CAN)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Melayu (MYS)"
//                , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
//        String[] notMessages = new String[]{"asdasdasd", "sss"};
//        String[] values = new String[]{"0", "5"};
//        String[] badValues = new String[]{"13s"};
//        product.Login.LanguageSelect.Has(texts, "option");
//        product.Login.LanguageSelect.DoesNotHave(notMessages, "option");
//        product.Login.LanguageSelect.DoesNotHave(badValues, "option", "value");
//        product.Login.LanguageSelect.Has(values, "option", "value");
//        product.Login.LanguageSelect.HasOnly(texts, "option");
//        product.Login.LanguageSelect.HasOnly(new String[]{"1"}, "option[value='1']", "value");
//        product.Login.PasswordTextBox.Is("ctl00_Content_Login1_Password", "id");
//    }

//    @Test
//    public void TestGoBackGoForward_01() {
//        String[] texts = {"English (USA)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Italiano (ITA)", "Melayu (MYS)"
//                , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
//        String[] shouldfail = {"Klingon", "African Clicky Noises", "Reptilian Hissing"};
//        String[] values = {"1", "2"};
//        String[] valuesShouldFail = {"-12", "Blue"};
//        product.Login.LanguageSelect.Click();
//        product.Login.LanguageSelect.HasOptions(texts, null, WebSelectOption.Text);
//        product.Login.LanguageSelect.HasOptions(values, null, WebSelectOption.Value);
//        product.Login.LanguageSelect.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
//        product.Login.LanguageSelect.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
//        product.Login.LoginButton.Click();
//        product.browser.GoBack();
//        product.browser.GoForward();
//        //product.Home.ViewPayStatement.Click();
//        //System.out.println("After Click");
//    }


}
