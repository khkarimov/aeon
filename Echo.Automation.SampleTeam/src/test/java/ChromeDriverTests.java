import echo.core.common.exceptions.NotAllPopupWindowsClosedException;
import echo.core.common.exceptions.TimeoutExpiredException;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static echo.core.test_abstraction.product.Echo.Launch;
import static org.hamcrest.core.Is.is;

public class ChromeDriverTests {

    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void SetUp() {
    }

    @AfterClass
    public static void TearDown() {
        //product.browser.Quit();
    }

    @Before
    public void BeforeTests() {
        product = Launch(Sample.class, BrowserType.Chrome);
        product.browser.Maximize();
        product.browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void AfterTests() {
        product.browser.Quit();
    }
//endregion

    @Test
    public void Test_SwitchToMainWindow_SwitchToWindowByTitle_VerifyTitle(){
        product.browser.VerifyTitle("Material Design Lite");
        product.StartPage.popupButton.Click();
        product.browser.SwitchToWindowByTitle("HeatLogo.jpg");
        product.browser.VerifyTitle("HeatLogo.jpg (300×200)");
        product.browser.SwitchToMainWindow();
        product.browser.VerifyTitle("Material Design Lite");
        product.browser.SwitchToWindowByUrl("HeatLogo.jpg");
        product.browser.VerifyTitle("HeatLogo.jpg (300×200)");
        product.browser.SwitchToMainWindow();
        product.browser.VerifyTitle("Material Design Lite");
        product.browser.SwitchToWindowByTitle("HeatLogo.jpg");
        product.browser.Close();
        product.browser.SwitchToMainWindow(true);
    }

    @Test
    public void TestSwitchToMainWindow_NotAllPopupsClosedException(){
        product.StartPage.popupButton.Click();
        product.browser.SwitchToWindowByTitle("HeatLogo.jpg");
        thrown.expectCause(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.SwitchToMainWindow(true);
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
    public void TestHasOptionsCommandAndDoesNotHaveOptions_Success() {
        String[] texts = {"option499", "option8"};
        String[] shouldfail = {"Klingon", "Clicky Noises", "Reptilian Hissing"};
        String[] values = {"1", "2"};
        String[] valuesShouldFail = {"-12", "Blue"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(texts, null, WebSelectOption.Text);
        product.StartPage.DropDown.HasOptions(values, null, WebSelectOption.Value);
        product.StartPage.DropDown.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
        product.StartPage.DropDown.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
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
    public void TestClickAndHold() {
        product.StartPage.Start.ClickAndHold(5000);
    }


}
