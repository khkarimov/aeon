/**
 * Created by SebastianR on 6/6/2016.
 */

import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import main.Sample;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.Cookie;
import java.util.Date;

import static echo.core.test_abstraction.product.Echo.Launch;

public class CommandTesting {

    private static Sample product;

//region Setup and Teardown
    @BeforeClass
    public static void SetUp(){
    }

    @AfterClass
    public static void TearDown(){
        product.Browser.Quit();
    }

    @Before
    public void BetweenTests(){
        product = Launch(Sample.class, BrowserType.Firefox);
        product.Browser.Maximize();
        product.Browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void AfterTests() {
        product.Browser.Quit();
    }
//endregion

    @Test
    public void TestSendKeysToAlert_AcceptAlertWhenThereIsAnAlert(){
        product.Browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.SendKeysToAlert("Tester of Alerts");
        product.Browser.AcceptAlert();
    }

//region Ignore Test
    @Ignore
    public void TestOpenFileDialog() {
        product.StartPage.TestFileDialogInput.OpenFileDialog();
    }

    @Ignore
    public void TestDismissAlertWhenThereIsAnAlert(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.DismissAlert();
    }

    @Ignore
    public void TestBlurTextBox(){
        product.StartPage.AlertTitleTextBox.Set("Alert Test Title");
        product.StartPage.AlertTitleTextBox.Blur();
    }

    @Ignore
    public void MouseOverButton_ButtonTextChangesColor() {
        product.StartPage.Start.MouseOver();
        product.StartPage.Start.MouseOut();
    }
//endregion

    @Test
    public void TestHasOptionsCommandsWith50000() {
        String [] texts = {"option499", "option8"};
        String [] shouldfail = {"Klingon", "Clicky Noises", "Reptilian Hissing"};
        String [] values = {"1", "2"};
        String [] valuesShouldFail = {"-12", "Blue"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(texts, null,  WebSelectOption.Text);
        product.StartPage.DropDown.HasOptions(values, null,  WebSelectOption.Value);
        product.StartPage.DropDown.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
        product.StartPage.DropDown.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
    }

    @Test
    public void TestWindowResizingAndNavigation() {
        product.Browser.Resize(BrowserSize.TabletLandscape);
        product.Browser.Resize(BrowserSize.SmallTabletLandscape);
        product.Browser.Resize(BrowserSize.MobileLandscape);
        product.Browser.Maximize();
        product.Browser.GoToUrl("http://www.tutorialspoint.com");
        product.Browser.ScrollToEnd();
        product.Browser.ScrollToTop();
        product.Browser.GoBack();
        product.Browser.GoForward();
    }

    @Test
    public void TestSelectFileDialog(){
        String path = System.getProperty("user.dir") + "\\Test Sample Context\\HeatLogo.jpg";
        product.StartPage.TestFileDialogInput.OpenFileDialog();
        product.StartPage.TestFileDialogInput.SelectFileDialog(path);
    }

    @Test
    public void TestDragAndDropNotUsingHTML5Events(){
        product.Browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.DragAndDrop(By.CssSelector("ul[id='box2']"));
    }

    @Test
    public void TestNotEnabled(){
        product.StartPage.DisabledButton.IsDisabled();
        //product.StartPage.DisabledButton.IsEnabled();
        product.StartPage.Start.IsEnabled();
    }

    @Test
    public void TestClickAndHold(){
        product.StartPage.Start.ClickAndHold(9000);
    }

    @Test
    public void Has() {
        product.StartPage.div.Has(new String[]{"Async Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.Has(new String[]{"start"}, "button", "id");
        product.StartPage.div.HasLike(new String[]{"ASYNC Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.HasLike(new String[]{"START"}, "button", "id");
        product.StartPage.DropDown.Is("drop-down-list", "id");
        product.StartPage.DropDown.IsLike("DROP-DOWN-LIST", "id");
        product.StartPage.div.DoesNotHave(new String[]{"ASYNC CALL 1"}, "h3");
        product.StartPage.div.DoesNotHaveLike(new String[]{"async call 3"}, "h3");
    }

    @Test
    public void TestVerifyAlertTextWithCorrectText(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.VerifyAlertText("Send some keys");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyAlertTextWithIncorrectText(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.VerifyAlertText("Send other keys");
    }

    @Test
    public void TestVerifyAlertTextLikeWithCorrectText(){
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.VerifyAlertTextLike("Send some keys", true);
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyAlertTextLikeWithIncorrectText() {
        product.StartPage.OpenAlertButton.Click();
        product.Browser.VerifyAlertExists();
        product.Browser.VerifyAlertTextLike("send some keys", true);
    }

    @Test
    public void TestVerifyTitleWithCorrectTitle(){
        product.Browser.VerifyTitle("Material Design Lite");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyTitleWithIncorrectTitle(){
        product.Browser.VerifyTitle("Material Design");
    }

    @Test
    public void TestVerifyURLWithCorrectURL(){
        product.Browser.GoToUrl("https://www.google.com");
        product.Browser.VerifyURL("https://www.google.com/");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyURLWithIncorrectURL() {
        product.Browser.VerifyURL("https://www.google.com");
    }

    @Ignore
    public void TestUploadFile() {
        product.StartPage.TestFileDialogInput.UploadFileDialog("asdasd#@$@#$");
    }

    @Test
    public void TestCheckCommand() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Uncheck();
    }

    @Test
    public void TestClickAllElements() {
        product.Browser.ClickAllElementsCommand(By.CssSelector("input[id='checkbox']"));
    }
}

    @Test
    public void CookieTests() {
        product.Browser.GoToUrl("http://google.com");
        IWebCookie cookie = new IWebCookie() {
            String name = "CookieName";
            String domain = ".google.com";
            String value = "CookieValue";
            Date expiration = new Date(2099, 1, 1, 1, 1);
            String path = "/";
            boolean secure = false;
            boolean session = true;
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String getPath() {
                return path;
            }

            @Override
            public String getDomain() {
                return domain;
            }

            @Override
            public Date getExpiration() {
                return expiration;
            }

            @Override
            public boolean getSecure() {
                return secure;
            }

            @Override
            public boolean getSession() {
                return session;
            }
        };
        product.Browser.AddCookie(cookie);
        IWebCookie secondCookie = product.Browser.GetCookie(cookie.getName());
        assert (secondCookie.getName().equals(cookie.getName()));
        assert (secondCookie.getDomain().equals(cookie.getDomain()));
        assert (secondCookie.getValue().equals(cookie.getValue()));
        assert (secondCookie.getSecure() == cookie.getSecure());
        assert (secondCookie.getPath().equals(cookie.getPath()));
        assert (secondCookie.getExpiration().equals(cookie.getExpiration()));

        product.Browser.ModifyCookie(cookie.getName(), "CookieNewValue");
        secondCookie = product.Browser.GetCookie(cookie.getName());
        assert (secondCookie.getValue().equals("CookieNewValue"));
        product.Browser.DeleteCookie(cookie.getName());
    }

    @Test
    public void CheckBox() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Is("");
    }
}
