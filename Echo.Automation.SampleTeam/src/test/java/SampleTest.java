import echo.core.common.web.BrowserSize;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import main.Sample;
import main.vTeamSamplePage;
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

    @BeforeClass
    public static void fixtureSetUp() {
    }
    @Before
    public void StartUp () {
        product = Launch(Sample.class, Firefox);
        product.Browser.GoToUrl("http://srenv02web.newgen.corp/login.aspx");
    }
    @After
    public void TearDown() {
        product.Browser.Quit();
    }

    @Test
    public void TestGoBackGoForward_01() {
        String [] texts = {"English (USA)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Italiano (ITA)", "Melayu (MYS)"
        , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
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
    public void TestDoubleClickScrollTopEnd_04() throws InterruptedException {
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("password");
        product.Login.LoginButton.MouseOver();
        product.Login.LoginButton.MouseOut();
        product.Browser.GoToUrl("http://www.tutorialspoint.com");
        product.Browser.ScrollToEnd();
        product.Browser.ScrollToTop();
    }

    @Test
    public void TestVisibleAndNotVisible_05() {
        product.Login.WarningMessage.NotVisible();
        product.Login.UserNameTextBox.Set("usa-canu");
        product.Login.PasswordTextBox.Set("FAIL");
        product.Login.LoginButton.Click();
        product.Login.WarningMessage.Visible();
    }

    @Test
    //Only works on vTeam Sample Page
    public void TestSelectedAndNotSelected_06() {
//        product.StartPage.Checkbox100.NotSelected();
//        product.StartPage.Checkbox100.Check();
//        product.StartPage.Checkbox100.Selected();
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
        System.out.println("User Name text box name value: " + testAttributeValues);
    }

    @Test
    public void TestDragAndDropNotUsingHTML5Events(){
        product.Browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.DragAndDrop(By.CssSelector("ul[id='box2']"));
    }
    @Test
    public void TestHasElementsInOrder() {
        String [] options = new String [] {"English (USA)", "Italiano (ITA)", "Melayu (MYS)"};
        String [] values = new String [] {"0", "5", "13"};
        String [] badValues = new String [] {"0", "13", "5"};
        String [] badOptions = new String [] {"English (USA)", "Melayu (MYS)", "Italiano (ITA)"};
        String [] value = new String [] {"0"};
        String [] option = new String [] {"English (USA)"};
        product.Login.LanguageSelect.HasOptionsInOrder(options, WebSelectOption.Text);
        product.Login.LanguageSelect.HasOptionsInOrder(values, WebSelectOption.Value);
        product.Login.LanguageSelect.HasOptionsInOrder(value, WebSelectOption.Value);
        product.Login.LanguageSelect.HasOptionsInOrder(option, WebSelectOption.Text);
    }

    @Test
    public void TestHas() {
        String [] texts = {"English (USA)", "English (GBR)", "English (CAN)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Melayu (MYS)"
                , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
        String [] notMessages = new String [] {"asdasdasd", "sss"};
        String [] values = new String [] {"0", "5"};
        String [] Badvalues = new String [] {"13s"};
        product.Login.LanguageSelect.Has(texts, "option");
        product.Login.LanguageSelect.DoesNotHave(notMessages, "option");
        product.Login.LanguageSelect.DoesNotHave(Badvalues, "option", "value");
        product.Login.LanguageSelect.Has(values, "option", "value");
        product.Login.LanguageSelect.HasOnly(texts, "option");
        product.Login.LanguageSelect.HasOnly(new String [] {"1"}, "option[value='1']", "value");
        product.Login.PasswordTextBox.Is("ctl00_Content_Login1_Password", "id");
    }
}
