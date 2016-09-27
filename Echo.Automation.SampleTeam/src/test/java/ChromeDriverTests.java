import echo.core.command_execution.commands.web.GetElementAttributeCommand;
import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.*;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
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
    public void Test_SwitchToMainWindow_SwitchToWindowByTitle_VerifyTitle() {
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
    public void TestSwitchToMainWindow_NotAllPopupsClosedException() {
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
        product.StartPage.DraggableListItem.DragAndDrop("ul[id='box2']");
    }

    @Test
    public void TestClickAndHold() {
        product.StartPage.Start.ClickAndHold(5000);
    }

    @Test
    public void TestDatesApproximatelyEqualsCommand() {
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-31"), Period.days(0));
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-26"), Period.days(5));
        thrown.expectCause(IsInstanceOf.instanceOf(DatesNotApproximatelyEqualException.class));
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-08"), Period.days(5));
    }

    @Test
    public void TestGetBrowserType() {
        assert (product.browser.GetBrowserType().equals(BrowserType.Chrome));
    }

    @Test
    public void TestGetClientRects() {
        // gotta think about how to test this
    }

    //region Test for IsNotLikeCommand
    @Test
    public void TestIsNotLikeCommandWithCorrectValues() {
        product.StartPage.UltimateLogoImage.IsNotLike("pass", "src");
        product.StartPage.DisabledButton.IsNotLike("Enabled");
        product.StartPage.DisabledButton.IsNotLike("Disabledd");
    }

    @Test
    public void TestIsNotLikeCommandWithIncorrectCase() {
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreAlikeException.class));
        product.StartPage.DisabledButton.IsNotLike("disabled");
    }

    @Test
    public void TestIsNotLikeCommandWithIncorrectValue() {
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreAlikeException.class));
        product.StartPage.DisabledButton.IsNotLike("Disabled");
    }
    //endregion

    //region Test for IsLikeCommand
    @Test
    public void TestIsLikeWithCorrectValues() {
        product.StartPage.UltimateLogoImage.IsLike("file:///C:/Projects/javaecho/Echo.Automation.SampleTeam/Test%20Sample%20Context/ultimate-image.png", "src");
        product.StartPage.UltimateLogoImage.IsLike("file:///C:/Projects/javaecho/Echo.Automation.SampleTeam/Test%20Sample%20Context/ULTIMATE-image.png", "src");
        product.StartPage.DisabledButton.IsLike("Disabled");
        product.StartPage.DisabledButton.IsLike("DISABLED");
    }

    @Test
    public void TestIsLikeCommandWithIncorrectValues() {
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotAlikeException.class));
        product.StartPage.DisabledButton.IsLike("fail");
    }
    //endregion

    //region Test for IsCommand
    @Test
    public void TestIsCommandWithCorrectValues() {
        product.StartPage.DisabledButton.Is("Disabled");
        product.StartPage.popupButton.Is("Popup Button");
    }
    //endregion

    @Test
    public void TestNotVisible_Correct() {
        product.StartPage.InvisibleButton.NotVisible();
    }

    @Test
    public void TestNotVisible_Incorrect() {
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsVisibleException.class));
        product.StartPage.OpenAlertButton.NotVisible();
    }

    @Test
    public void TestVisible_Correct() {
        product.StartPage.DateLabel.Visible();
    }

    @Test
    public void TestVisible_Incorrect() {
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotVisibleException.class));
        product.StartPage.InvisibleButton.Visible();
    }

    @Test
    public void TestRightClick() {
        product.StartPage.DateLabel.RightClick();
    }

    @Test
    public void TestNotExist_Correct() {
        product.StartPage.NonExistentLabel.NotExists();
    }

    @Test
    public void TestNotExist_Incorrect() {
        thrown.expectCause(IsInstanceOf.instanceOf(ElementExistsException.class));
        product.StartPage.DateLabel.NotExists();
    }

    @Test
    public void TestDoubleClick() {
        product.StartPage.UltimateLogoImage.DoubleClick();
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void TestPressKeyboardKey() {
        product.StartPage.FormTextBox.PressKeyboardKey(KeyboardKey.SPACE);
        product.StartPage.FormTextBox.PressKeyboardKey(KeyboardKey.SPACE);
    }

    @Test
    public void TestClickAllElements() {
        product.StartPage.LexoDropDown.ClickAllElements();
    }

    @Test
    public void TestGetElementAttributes() {
        String classAttribute = product.StartPage.FormTextBox.GetElementAttribute("class").toString();
        assert (classAttribute.equals("mdl-textfield__input"));
    }

    @Test
    public void TestSelected_AfterCheck() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Selected();
        product.StartPage.NextRadioButton.Check();
        product.StartPage.NextRadioButton.Selected();
    }

    @Test
    public void TestNotSelected() {
        product.StartPage.TestCheckbox.NotSelected();
        product.StartPage.NextRadioButton.NotSelected();
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Uncheck();
        product.StartPage.TestCheckbox.NotSelected();
        product.StartPage.NextRadioButton.NotSelected();
    }
}
