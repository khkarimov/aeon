import aeon.core.common.CompareType;
import aeon.core.common.KeyboardKey;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserSize;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.WebSelectOption;
import aeon.core.framework.abstraction.controls.web.IWebCookie;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;

import static aeon.core.testabstraction.product.Aeon.launch;

public class ChromeDriverTests {

    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
        //product.browser.quit();
    }

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.Chrome);
        product.browser.maximize();
        product.browser.goToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }
    //endregion

    @Test
    public void testAcceptAlert_VerifyAlertExists_VerifyAlertNotExists() {
        product.browser.verifyAlertNotExists();
        product.StartPage.OpenAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.acceptAlert();
    }

    @Test
    public void testSendKeysToAlert_VerifyAlertExists_VerifyAlertNotExists () {
        product.browser.verifyAlertNotExists();
        product.StartPage.OpenAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.sendKeysToAlert("Tester of Alerts");
        product.browser.acceptAlert();
    }

    @Test
    public void testAddCookie_ModifyCookie_DeleteCookie_GetCookie() {
        product.browser.goToUrl("http://google.com");
        IWebCookie cookie = new IWebCookie() {
            String name = "CookieName";
            String domain = ".google.com";
            String value = "CookieValue";
            Date expiration = getNextYear();
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

            private Date getNextYear() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(Long.parseLong("18000000")));
                int yearsSinceEpoch = DateTime.now().getYear() - cal.get(Calendar.YEAR);
                cal.add(Calendar.YEAR, yearsSinceEpoch + 1);
                return cal.getTime();
            }
        };
        product.browser.addCookie(cookie);
        IWebCookie secondCookie = product.browser.getCookie(cookie.getName());
        assert (secondCookie.getName().equals(cookie.getName()));
        assert (secondCookie.getDomain().equals(cookie.getDomain()));
        assert (secondCookie.getValue().equals(cookie.getValue()));
        assert (secondCookie.getSecure() == cookie.getSecure());
        assert (secondCookie.getPath().equals(cookie.getPath()));
        assert (secondCookie.getExpiration().equals(cookie.getExpiration()));

        product.browser.modifyCookie(cookie.getName(), "CookieNewValue");
        secondCookie = product.browser.getCookie(cookie.getName());
        assert (secondCookie.getValue().equals("CookieNewValue"));
        product.browser.deleteCookie(cookie.getName());
    }

    @Test
    public void testDatesApproximatelyEquals() {
        product.StartPage.DateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-31"), Period.days(0));
        product.StartPage.DateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-26"), Period.days(5));
        thrown.expectCause(IsInstanceOf.instanceOf(DatesNotApproximatelyEqualException.class));
        product.StartPage.DateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-08"), Period.days(5));
    }

    @Test
    public void testBlur() {
        //used to be set command
        product.StartPage.AlertTitleTextBox.click();
        product.StartPage.AlertTitleTextBox.blur();
    }

    @Test
    public void testCheck_UnCheck() {
        product.StartPage.TestCheckbox.check();
        product.StartPage.TestCheckbox.uncheck();
    }

    @Test
    public void testClickAndHold() {
        product.StartPage.Start.clickAndHold(5000);
    }

    @Test
    public void testDisabled() {
        product.StartPage.DisabledButton.isDisabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsEnabledException.class));
        product.StartPage.Start.isDisabled();
    }

    @Test
    public void testDismissAlertWhenThereIsAnAlert() {
        product.StartPage.OpenAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.dismissAlert();
    }

    @Test
    public void testHas_HasLike_Is_IsLike_IsNotLike_DoesNotHave_DoesNotHaveLike() {
        product.StartPage.div.has(new String[]{"Async Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.has(new String[]{"start"}, "button", "id");
        product.StartPage.div.hasLike(new String[]{"ASYNC Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.StartPage.div.hasLike(new String[]{"START"}, "button", "id");
//        product.StartPage.DropDown.is("drop-down-list", "id");
//        product.StartPage.DropDown.isLike("DROP-DOWN-LIST", "id");
//        product.StartPage.DropDown.isNotLike("DROP-DOWN-LISTT", "id");
//        product.StartPage.div.doesNotHave(new String[]{"ASYNC CALL 1"}, "h3");
//        product.StartPage.div.doesNotHaveLike(new String[]{"async call 3"}, "h3");
    }

    @Test
    public void testDoubleClick() {
        product.StartPage.UltimateLogoImage.doubleClick();
        String src = product.StartPage.UltimateLogoImageDoubleClick.getElementAttribute("src").toString();
        assert(src.contains("ultimate-image.png"));
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void testDragAndDrop() {
        product.browser.goToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.StartPage.DraggableListItem.dragAndDrop("ul[id='box2']");
    }

    @Test
    public void testEnabled() {
        product.StartPage.Start.isEnabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotEnabledException.class));
        product.StartPage.DisabledButton.isEnabled();
    }

    @Test
    public void testExists(){
        product.StartPage.Start.exists();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.StartPage.NonExistentLabel.exists();
    }

    @Test
    public void testGetAlertText() {
        product.StartPage.OpenAlertButton.click();
        String text = product.browser.getAlertText();

        assert(text.equals("Send some keys"));
        product.browser.dismissAlert();
        thrown.expectCause(IsInstanceOf.instanceOf(NoAlertException.class));
        product.browser.getAlertText();
    }

    @Test
    public void testGetBrowserType() {
        assert (product.browser.getBrowserType().equals(BrowserType.Chrome));
    }

    @Test
    public void testGetElementAttributes() {
        String classAttribute = product.StartPage.FormTextBox.getElementAttribute("class").toString();
        assert (classAttribute.equals("mdl-textfield__input"));
    }

    @Test
    public void testWindowResizing_GoBack_GoForward_ScrollToEnd_ScrollToTop() {
        product.browser.resize(BrowserSize.TabletLandscape);
        product.browser.resize(BrowserSize.SmallTabletLandscape);
        product.browser.resize(BrowserSize.MobileLandscape);
        product.browser.maximize();
        product.browser.goToUrl("http://www.tutorialspoint.com");
        product.browser.scrollToEnd();
        product.browser.scrollToTop();
        product.browser.goBack();
        product.browser.goForward();
    }

    @Test
    public void testMouseOver_MouseOut_Refresh() {
        product.StartPage.Start.mouseOver();
        product.StartPage.Start.mouseOut();
        product.browser.refresh();
    }

    @Test
    public void testNotExists(){
        product.StartPage.NonExistentLabel.notExists();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementExistsException.class));
        product.StartPage.Start.notExists();
    }

    @Test
    public void testNotSelected() {
        product.StartPage.TestCheckbox.notSelected();
        product.StartPage.NextRadioButton.notSelected();
        product.StartPage.TestCheckbox.check();
        product.StartPage.TestCheckbox.uncheck();
        product.StartPage.TestCheckbox.notSelected();
        product.StartPage.NextRadioButton.notSelected();
    }

    @Test
    public void testNotVisible() {
        product.StartPage.InvisibleButton.notVisible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsVisibleException.class));
        product.StartPage.OpenAlertButton.notVisible();
    }

    @Test
    public void testSelected() {
        product.StartPage.TestCheckbox.check();
        product.StartPage.TestCheckbox.selected();
        product.StartPage.NextRadioButton.check();
        product.StartPage.NextRadioButton.selected();
    }

    @Test
    public void testPressKeyboardKey() {
        product.StartPage.FormTextBox.pressKeyboardKey(KeyboardKey.SPACE);
        product.StartPage.FormTextBox.pressKeyboardKey(KeyboardKey.SPACE);
    }

    @Test
    public void testRightClick() {
        product.StartPage.DateLabel.rightClick();
        String validationText = product.StartPage.ReactionLabel.getElementAttribute("textContent").toString();
        assert(validationText.equals("right click"));
    }

    @Test
    public void testSetWithNonSelectElement_Clear() {
        product.StartPage.FormTextBox.set("set the value to this");
        product.StartPage.FormTextBox.clear();
    }

    @Ignore
    public void testUploadFile() {
        product.StartPage.TestFileDialogInput.uploadFileDialog("asdasd#@$@#$");
    }

    @Test
    public void testVerifyAlertText() {
        product.StartPage.OpenAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertText("Send some keys");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyAlertText("Send other keys");
        product.browser.acceptAlert();
    }

    @Test
    public void testVerifyAlertTextLike() {
        product.StartPage.OpenAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertTextLike("Send some keys", true);
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotAlikeException.class));
        product.browser.verifyAlertTextLike("send some keys", true);
        product.browser.acceptAlert();
    }

    @Test
    public void testVerifyTitle() {
        product.browser.verifyTitle("Material Design Lite");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyTitle("Fake Title");
    }

    @Test
    public void testVerifyURL() {
        product.browser.goToUrl("http://www.espn.com/");
        product.browser.verifyURL("http://www.espn.com/");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyURL("http://www.espne.com/");
    }

    @Test
    public void testVisible_Correct() {
        product.StartPage.DateLabel.visible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotVisibleException.class));
        product.StartPage.InvisibleButton.visible();
    }

    @Test
    public void testSwitchToMainWindow() {
        product.browser.verifyTitle("Material Design Lite");
        product.StartPage.PopupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        product.browser.switchToMainWindow();
        product.browser.verifyTitle("Material Design Lite");
        product.browser.switchToWindowByTitle("Google");
        product.browser.close();
        product.browser.switchToMainWindow(true);
        product.StartPage.PopupButton.click();
        product.browser.switchToWindowByTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.switchToMainWindow(true);
    }

    @Test
    public void testSwitchToWindowByTitle() {
        product.browser.verifyTitle("Material Design Lite");
        product.StartPage.PopupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByTitle("Some Fake Title");
    }


    @Test
    public void testSwitchToWindowByUrl() {
        product.browser.verifyTitle("Material Design Lite");
        product.StartPage.PopupButton.click();
        product.browser.switchToWindowByUrl("https://www.google.com");
        product.browser.verifyTitle("Google");
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByUrl("www.fake.com");
    }

    @Test
    public void testVerifyWindowDoesNotExistByUrl_VerifyWindowDoesNotExistByTitle() {
        product.browser.verifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.verifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void testHasOptions_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.StartPage.DropDown.click();
        product.StartPage.DropDown.hasOptions(validOptionValues, null, WebSelectOption.Value);
        String[] invalidOptionValues = {"0", "1", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.hasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testHasOptions_ByText() {
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        product.StartPage.DropDown.click();
        product.StartPage.DropDown.hasOptions(validOptionTexts, null, WebSelectOption.Text);
        String[] invalidOptions = {"option0", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.hasOptions(invalidOptions, null, WebSelectOption.Text);
    }

    @Test
    public void testDoesNotHaveOptions_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.StartPage.DropDown.click();
        product.StartPage.DropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
        String[] validOptionValues = {"1", "2", "49"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testDoesNotHaveOptions_ByText() {
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        product.StartPage.DropDown.click();
        product.StartPage.DropDown.doesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.doesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending(){
        product.StartPage.LexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending(){
        product.StartPage.RevLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Ascending(){
        product.StartPage.LexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Descending(){
        product.StartPage.RevLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
    }

    @Test
    public void testHasNumberOfOptions(){
        int totalOptions = 5000;
        product.StartPage.DropDown.hasNumberOfOptions(totalOptions);
        totalOptions = 50;
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.StartPage.DropDown.hasNumberOfOptions(totalOptions);
    }

    @Test
    public void testHasOptionsInOrder_ByText() {
        String[] validOptions = {"option1", "option2", "option3"};
        product.StartPage.DropDown.hasOptionsInOrder(validOptions, WebSelectOption.Text);
        String[] invalidoptions = {"option1", "option4", "option2"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Text);
    }

    @Test
    public void testHasOptionsInOrder_ByValue(){
        String[] validoptions = {"1", "2", "3"};
        product.StartPage.DropDown.hasOptionsInOrder(validoptions, WebSelectOption.Value);
        String[] invalidoptions = {"1", "2", "3", "40", "5"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Value);
    }

    @Test
    public void testSet_WithSelect(){
        product.StartPage.LexoDropDown.set(WebSelectOption.Value, "10");
        product.StartPage.LexoDropDown.set(WebSelectOption.Text, "dog");
        product.StartPage.LexoDropDown.set(WebSelectOption.Text, "zebra");
    }

    @Test
    public void testSetValueByJavaScript(){
        product.StartPage.FormTextBox.setTextByJavaScript("set text by javascript is working");
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect(){
        product.StartPage.LexoDropDown.is("apple");
        product.StartPage.LexoDropDown.isLike("APPLE");
        product.StartPage.LexoDropDown.is("01", "value");
        product.StartPage.LexoDropDown.isNotLike("appple");
        product.StartPage.LexoDropDown.set(WebSelectOption.Text, "zebra");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.StartPage.LexoDropDown.is("ZEBRA");

    }

    @Test
    public void testWaiter(){
        product.StartPage.Start.click();
        product.StartPage.SmileyFace1.click();
    }

    @Test
    public void testGrids(){
        product.StartPage.myGrid.RowBy.index(2).checkBoxButton.click();
        product.StartPage.myGrid.RowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.click();
        product.StartPage.myGrid.RowBy.material("Laminate").quantity("9").getRow().checkBoxButton.click();
        product.StartPage.myGrid.RowBy.material("Acrylic").getRow().exists();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.StartPage.myGrid.RowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.click();

    }

    @Test
    public void setByDivJavaScript() {
        product.StartPage.DivWindow.setDivValueByJavaScript("Hello World Haha");
        product.StartPage.DivWindow.is("Hello World Haha");
        product.StartPage.BodyTag.setDivValueByJavaScript("Hello World Haha");
        product.StartPage.BodyTag.is("Hello World Haha");
    }

    @Test
    public void setByBodyJavaScript() {
        product.StartPage.BodyTag.setBodyValueByJavaScript("Hello World Haha");
        product.StartPage.BodyTag.is("Hello World Haha");
    }
}
