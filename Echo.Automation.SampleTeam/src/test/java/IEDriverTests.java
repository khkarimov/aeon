import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import main.Sample;
import org.junit.*;

import java.util.Date;

import static echo.core.test_abstraction.product.Echo.Launch;

public class IEDriverTests {
    private static Sample product;

    //region Setup and Teardown
    @BeforeClass
    public static void SetUp() {
    }

    @AfterClass
    public static void TearDown() {
        product.browser.Quit();
    }

    @Before
    public void BeforeTests() {
        product = Launch(Sample.class, BrowserType.InternetExplorer);
        product.browser.Maximize();
        product.browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void AfterTests() {
        product.browser.Quit();
    }
//endregion

    @Test
    public void CheckBox() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Is("");
    }

    @Test
    public void TestVerifyWindowDoesNotExist() {
        product.browser.VerifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.VerifyWindowDoesNotExistByUrl("fakeUrl");
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
        product.StartPage.DisabledButton.Exists();
        product.browser.ClickAllElementsCommand(By.CssSelector("input[id='checkbox']"));
    }

    @Test
    public void TestWindowResizingAndNavigation() {
        product.browser.Resize(BrowserSize.TabletLandscape);
        product.browser.Resize(BrowserSize.SmallTabletLandscape);
        product.browser.Resize(BrowserSize.MobileLandscape);
        product.browser.Maximize();
        product.browser.GoToUrl("http://www.tutorialspoint.com");
        product.browser.ScrollToEnd();
        product.browser.ScrollToTop();
        product.browser.GoBack();
        product.browser.GoForward();
    }



    @Test
    public void TestDragAndDropNotUsingHTML5Events() {
        product.browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.DragAndDrop(By.CssSelector("ul[id='box2']"));
    }

    @Test
    public void TestSendKeysToAlert_AcceptAlertWhenThereIsAnAlert() {
        product.browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.SendKeysToAlert("Tester of Alerts");
        product.browser.AcceptAlert();
    }

    @Test
    public void TestNotEnabled() {
        product.StartPage.DisabledButton.IsDisabled();
        //product.StartPage.DisabledButton.IsEnabled();
        product.StartPage.Start.IsEnabled();
    }

    @Test
    public void TestClickAndHold() {
        product.StartPage.Start.ClickAndHold(5000);
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
    public void TestVerifyAlertTextWithCorrectText() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertText("Send some keys");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyAlertTextWithIncorrectText() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertText("Send other keys");
    }

    @Test
    public void TestVerifyAlertTextLikeWithCorrectText() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertTextLike("Send some keys", true);
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyAlertTextLikeWithIncorrectText() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertTextLike("send some keys", true);
    }

    @Test
    public void TestVerifyTitleWithCorrectTitle() {
        product.browser.VerifyTitle("Material Design Lite");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyTitleWithIncorrectTitle() {
        product.browser.VerifyTitle("Material Design");
    }

    @Test
    public void TestVerifyURLWithCorrectURL() {
        product.browser.GoToUrl("https://www.google.com");
        product.browser.VerifyURL("https://www.google.com/");
    }

    @Test(expected = TimeoutExpiredException.class)
    public void TestVerifyURLWithIncorrectURL() {
        product.browser.VerifyURL("https://www.google.com");
    }
}
