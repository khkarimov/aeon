import echo.core.common.CompareType;
import echo.core.common.KeyboardKey;
import echo.core.common.exceptions.*;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.framework_abstraction.controls.web.IWebCookie;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static echo.core.test_abstraction.product.Aeon.Launch;

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
    public void TestAcceptAlert_VerifyAlertExists_VerifyAlertNotExists() {
        product.browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.AcceptAlert();
    }

    @Test
    public void TestSendKeysToAlert_VerifyAlertExists_VerifyAlertNotExists () {
        product.browser.VerifyAlertNotExists();
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.SendKeysToAlert("Tester of Alerts");
        product.browser.AcceptAlert();
    }

    @Test
    public void TestAddCookie_ModifyCookie_DeleteCookie_GetCookie() {
        product.browser.GoToUrl("http://google.com");
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
        product.browser.AddCookie(cookie);
        IWebCookie secondCookie = product.browser.GetCookie(cookie.getName());
        assert (secondCookie.getName().equals(cookie.getName()));
        assert (secondCookie.getDomain().equals(cookie.getDomain()));
        assert (secondCookie.getValue().equals(cookie.getValue()));
        assert (secondCookie.getSecure() == cookie.getSecure());
        assert (secondCookie.getPath().equals(cookie.getPath()));
        assert (secondCookie.getExpiration().equals(cookie.getExpiration()));

        product.browser.ModifyCookie(cookie.getName(), "CookieNewValue");
        secondCookie = product.browser.GetCookie(cookie.getName());
        assert (secondCookie.getValue().equals("CookieNewValue"));
        product.browser.DeleteCookie(cookie.getName());
    }

    @Test
    public void TestDatesApproximatelyEquals() {
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-31"), Period.days(0));
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-26"), Period.days(5));
        thrown.expectCause(IsInstanceOf.instanceOf(DatesNotApproximatelyEqualException.class));
        product.StartPage.DateLabel.DatesApproximatelyEqual("name", DateTime.parse("2016-08-08"), Period.days(5));
    }

    @Test
    public void TestBlur() {
        //used to be set command
        product.StartPage.AlertTitleTextBox.Click();
        product.StartPage.AlertTitleTextBox.Blur();
    }

    @Test
    public void TestCheck_UnCheck() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Uncheck();
    }

    @Test
    public void TestClickAndHold() {
        product.StartPage.Start.ClickAndHold(5000);
    }

    @Test
    public void TestDisabled() {
        product.StartPage.DisabledButton.IsDisabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsEnabledException.class));
        product.StartPage.Start.IsDisabled();
    }

    @Test
    public void TestDismissAlertWhenThereIsAnAlert() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.DismissAlert();
    }

    @Test
    public void TestHas_HasLike_Is_IsLike_IsNotLike_DoesNotHave_DoesNotHaveLike() {
        product.StartPage.div.Has(new String[]{"Async Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.Has(new String[]{"start"}, "button", "id");
        product.StartPage.div.HasLike(new String[]{"ASYNC Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.HasLike(new String[]{"START"}, "button", "id");
//        product.StartPage.DropDown.Is("drop-down-list", "id");
//        product.StartPage.DropDown.IsLike("DROP-DOWN-LIST", "id");
//        product.StartPage.DropDown.IsNotLike("DROP-DOWN-LISTT", "id");
//        product.StartPage.div.DoesNotHave(new String[]{"ASYNC CALL 1"}, "h3");
//        product.StartPage.div.DoesNotHaveLike(new String[]{"async call 3"}, "h3");
    }

    @Test
    public void TestDoubleClick() {
        product.StartPage.UltimateLogoImage.DoubleClick();
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void TestDragAndDrop() {
        product.browser.GoToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.DragAndDrop("ul[id='box2']");
    }

    @Test
    public void TestEnabled() {
        product.StartPage.Start.IsEnabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotEnabledException.class));
        product.StartPage.DisabledButton.IsEnabled();
    }

    @Test
    public void TestExists(){
        product.StartPage.Start.Exists();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.StartPage.NonExistentLabel.Exists();
    }

    @Test
    public void TestGetAlertText() {
        product.StartPage.OpenAlertButton.Click();
        String text = product.browser.GetAlertText();

        assert(text.equals("Send some keys"));
        product.browser.DismissAlert();
        thrown.expectCause(IsInstanceOf.instanceOf(NoAlertException.class));
        product.browser.GetAlertText();
    }

    @Test
    public void TestGetBrowserType() {
        assert (product.browser.GetBrowserType().equals(BrowserType.Chrome));
    }

    @Test
    public void TestGetElementAttributes() {
        String classAttribute = product.StartPage.FormTextBox.GetElementAttribute("class").toString();
        assert (classAttribute.equals("mdl-textfield__input"));
    }

    @Test
    public void TestWindowResizing_GoBack_GoForward_ScrollToEnd_ScrollToTop() {
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
    public void TestMouseOver_MouseOut_Refresh() {
        product.StartPage.Start.MouseOver();
        product.StartPage.Start.MouseOut();
        product.browser.Refresh();
    }

    @Test
    public void TestNotExists(){
        product.StartPage.NonExistentLabel.NotExists();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementExistsException.class));
        product.StartPage.Start.NotExists();
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

    @Test
    public void TestNotVisible() {
        product.StartPage.InvisibleButton.NotVisible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsVisibleException.class));
        product.StartPage.OpenAlertButton.NotVisible();
    }

    @Test
    public void TestSelected() {
        product.StartPage.TestCheckbox.Check();
        product.StartPage.TestCheckbox.Selected();
        product.StartPage.NextRadioButton.Check();
        product.StartPage.NextRadioButton.Selected();
    }

    @Test
    public void TestPressKeyboardKey() {
        product.StartPage.FormTextBox.PressKeyboardKey(KeyboardKey.SPACE);
        product.StartPage.FormTextBox.PressKeyboardKey(KeyboardKey.SPACE);
    }

    @Test
    public void TestRightClick() {
        product.StartPage.DateLabel.RightClick();
    }

    @Test
    public void TestSetWithNonSelectElement_Clear() {
        product.StartPage.FormTextBox.Set("Set the value to this");
        product.StartPage.FormTextBox.Clear();
    }

    @Ignore
    public void TestUploadFile() {
        product.StartPage.TestFileDialogInput.UploadFileDialog("asdasd#@$@#$");
    }

    @Test
    public void TestVerifyAlertText() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertText("Send some keys");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.VerifyAlertText("Send other keys");
        product.browser.AcceptAlert();
    }

    @Test
    public void TestVerifyAlertTextLike() {
        product.StartPage.OpenAlertButton.Click();
        product.browser.VerifyAlertExists();
        product.browser.VerifyAlertTextLike("Send some keys", true);
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotAlikeException.class));
        product.browser.VerifyAlertTextLike("send some keys", true);
        product.browser.AcceptAlert();
    }

    @Test
    public void TestVerifyTitle() {
        product.browser.VerifyTitle("Material Design Lite");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.VerifyTitle("Fake Title");
    }

    @Test
    public void TestVerifyURL() {
        product.browser.GoToUrl("https://www.google.com");
        product.browser.VerifyURL("https://www.google.com/");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.VerifyURL("https://www.googley.com");
    }

    @Test
    public void TestVisible_Correct() {
        product.StartPage.DateLabel.Visible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotVisibleException.class));
        product.StartPage.InvisibleButton.Visible();
    }

    @Test
    public void TestSwitchToMainWindow() {
        product.browser.VerifyTitle("Material Design Lite");
        product.StartPage.PopupButton.Click();
        product.browser.SwitchToWindowByTitle("Google");
        product.browser.VerifyTitle("Google");
        product.browser.SwitchToMainWindow();
        product.browser.VerifyTitle("Material Design Lite");
        product.browser.SwitchToWindowByTitle("Google");
        product.browser.Close();
        product.browser.SwitchToMainWindow(true);
        product.StartPage.PopupButton.Click();
        product.browser.SwitchToWindowByTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.SwitchToMainWindow(true);
    }

    @Test
    public void TestSwitchToWindowByTitle() {
        product.browser.VerifyTitle("Material Design Lite");
        product.StartPage.PopupButton.Click();
        product.browser.SwitchToWindowByTitle("Google");
        product.browser.VerifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.SwitchToWindowByTitle("Some Fake Title");
    }


    @Test
    public void TestSwitchToWindowByUrl() {
        product.browser.VerifyTitle("Material Design Lite");
        product.StartPage.PopupButton.Click();
        product.browser.SwitchToWindowByUrl("https://www.google.com");
        product.browser.VerifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.SwitchToWindowByUrl("www.fake.com");
    }

    @Test
    public void TestVerifyWindowDoesNotExistByUrl_VerifyWindowDoesNotExistByTitle() {
        product.browser.VerifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.VerifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void TestHasOptions_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionValues, null, WebSelectOption.Value);
        String[] invalidOptionValues = {"0", "1", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestHasOptions_ByText() {
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionTexts, null, WebSelectOption.Text);
        String[] invalidOptions = {"option0", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptions(invalidOptions, null, WebSelectOption.Text);
    }

    @Test
    public void TestDoesNotHaveOptions_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
        String[] validOptionValues = {"1", "2", "49"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.DoesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestDoesNotHaveOptions_ByText() {
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.DoesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void TestHasAllOptionsInOrder_ByValue_Ascending(){
        product.StartPage.LexoDropDown.HasAllOptionsInOrder(CompareType.AscendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_ByValue_Descending(){
        product.StartPage.RevLexoDropDown.HasAllOptionsInOrder(CompareType.DescendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_ByText_Ascending(){
        product.StartPage.LexoDropDown.HasAllOptionsInOrder(CompareType.AscendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void TestHasAllOptionsInOrder_ByText_Descending(){
        product.StartPage.RevLexoDropDown.HasAllOptionsInOrder(CompareType.DescendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.DescendingByText);
    }

    @Test
    public void TestHasNumberOfOptions(){
        int totalOptions = 5000;
        product.StartPage.DropDown.HasNumberOfOptions(totalOptions);
        totalOptions = 50;
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.StartPage.DropDown.HasNumberOfOptions(totalOptions);
    }

    @Test
    public void TestHasOptionsInOrder_ByText() {
        String[] validOptions = {"option1", "option2", "option3"};
        product.StartPage.DropDown.HasOptionsInOrder(validOptions, WebSelectOption.Text);
        String[] invalidoptions = {"option1", "option4", "option2"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptionsInOrder(invalidoptions, WebSelectOption.Text);
    }

    @Test
    public void TestHasOptionsInOrder_ByValue(){
        String[] validoptions = {"1", "2", "3"};
        product.StartPage.DropDown.HasOptionsInOrder(validoptions, WebSelectOption.Value);
        String[] invalidoptions = {"1", "2", "3", "40", "5"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptionsInOrder(invalidoptions, WebSelectOption.Value);
    }

    @Test
    public void TestSet_WithSelect(){
        product.StartPage.LexoDropDown.Set(WebSelectOption.Value, "10");
        product.StartPage.LexoDropDown.Set(WebSelectOption.Text, "dog");
        product.StartPage.LexoDropDown.Set(WebSelectOption.Text, "zebra");
    }

    @Test
    public void TestSetValueByJavaScript(){
        product.StartPage.FormTextBox.SetTextByJavaScript("Set text by javascript is working");
    }

    @Test
    public void TestIs_IsLike_IsNotLike_WithSelect(){
        product.StartPage.LexoDropDown.Is("apple");
        product.StartPage.LexoDropDown.IsLike("APPLE");
        product.StartPage.LexoDropDown.Is("01", "value");
        product.StartPage.LexoDropDown.IsNotLike("appple");
        product.StartPage.LexoDropDown.Set(WebSelectOption.Text, "zebra");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.StartPage.LexoDropDown.Is("ZEBRA");

    }

    @Test
    public void TestWaiter(){
        product.StartPage.Start.Click();
        product.StartPage.SmileyFace1.Click();
    }

    @Test
    public void TestGrids(){
        product.StartPage.myGrid.RowBy.index(2).checkBoxButton.Click();
        product.StartPage.myGrid.RowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.Click();
        product.StartPage.myGrid.RowBy.material("Laminate").quantity("9").getRow().checkBoxButton.Click();
        product.StartPage.myGrid.RowBy.material("Acrylic").getRow().Exists();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.StartPage.myGrid.RowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.Click();

    }

    @Test
    public void SetByDivJavaScript() {
        product.StartPage.DivWindow.SetDivValueByJavaScript("Hello World Haha");
        product.StartPage.DivWindow.Is("Hello World Haha");
        product.StartPage.BodyTag.SetDivValueByJavaScript("Hello World Haha");
        product.StartPage.BodyTag.Is("Hello World Haha");
    }

    @Test
    public void SetByBodyJavaScript() {
        product.StartPage.BodyTag.SetBodyValueByJavaScript("Hello World Haha");
        product.StartPage.BodyTag.Is("Hello World Haha");
    }
}
