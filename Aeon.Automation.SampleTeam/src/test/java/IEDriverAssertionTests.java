/**
 * Created by josepe on 5/4/2017.
 */
import aeon.core.common.CompareType;
import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserType;
import aeon.core.common.web.WebSelectOption;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static aeon.core.testabstraction.product.Aeon.launch;

public class IEDriverAssertionTests {
    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
        product.browser.quit();
    }

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.InternetExplorer);
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
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.acceptAlert();
    }

    @Test
    public void testSendKeysToAlert_VerifyAlertExists_VerifyAlertNotExists() {
        product.browser.verifyAlertNotExists();
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.sendKeysToAlert("Tester of Alerts");
    }

    @Test
    public void testDatesApproximatelyEquals() {
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-31"), Period.days(0));
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-26"), Period.days(5));
        thrown.expectCause(IsInstanceOf.instanceOf(DatesNotApproximatelyEqualException.class));
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-08"), Period.days(5));
    }

    @Test
    public void testDisabled() {
        product.startPage.disabledButton.isDisabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsEnabledException.class));
        product.startPage.start.isDisabled();
    }

    @Test
    public void testHas_HasLike_Is_IsLike_IsNotLike_DoesNotHave_DoesNotHaveLike() {
        product.startPage.div.has(new String[]{"Async Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.startPage.div.has(new String[]{"start"}, "button", "id");
        product.startPage.div.hasLike(new String[]{"ASYNC Call 1", "Async Call 2", "Async Call 2"}, "h3");
        product.startPage.div.hasLike(new String[]{"START"}, "button", "id");
        product.startPage.dropDown.is("drop-down-list", "id");
        product.startPage.dropDown.isLike("DROP-DOWN-LIST", "id");
        product.startPage.dropDown.isNotLike("THIS", "id");
        product.startPage.div.doesNotHave(new String[]{"ASYNC CALL 1"}, "h3");
        product.startPage.div.doesNotHaveLike(new String[]{"async call 3"}, "h3");
    }

    @Test
    public void testEnabled() {
        product.startPage.start.isEnabled();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotEnabledException.class));
        product.startPage.disabledButton.isEnabled();
    }

    @Test
    public void testExists(){
        product.startPage.start.exists();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.startPage.nonExistentLabel.exists();
    }

    @Test
    public void testGetBrowserType() {
        assert (product.browser.getBrowserType().equals(BrowserType.InternetExplorer));
    }

    @Test
    public void testGetElementAttributes() {
        String classAttribute = product.startPage.formTextBox.getElementAttribute("class").toString();
        assert (classAttribute.equals("mdl-textfield__input"));
    }

    @Test
    public void testNotExists(){
        product.startPage.nonExistentLabel.notExists();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementExistsException.class));
        product.startPage.start.notExists();
    }

    @Test
    public void testNotSelected() {
        product.startPage.testCheckbox.notSelected();
        product.startPage.nextRadioButton.notSelected();
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.uncheck();
        product.startPage.testCheckbox.notSelected();
        product.startPage.nextRadioButton.notSelected();
    }

    @Test
    public void testNotVisible() {
        product.startPage.invisibleButton.notVisible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementIsVisibleException.class));
        product.startPage.openAlertButton.notVisible();
    }

    @Test
    public void testSelectFileDialog_OpenFileDialog() {
        String path = System.getProperty("user.dir") + "\\Test Sample Context\\HeatLogo.jpg";
        product.startPage.testFileDialogInput.openFileDialog();
        product.startPage.testFileDialogInput.selectFileDialog(path);
    }

    @Test
    public void testSelected() {
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.selected();
        product.startPage.nextRadioButton.check();
        product.startPage.nextRadioButton.selected();
    }

    @Test
    public void testVerifyAlertText() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertText("Send some keys");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyAlertText("Send other keys");
    }

    @Test
    public void testVerifyAlertTextLike() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertTextLike("Send some keys", true);
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotAlikeException.class));
        product.browser.verifyAlertTextLike("send some keys", true);
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
        product.startPage.dateLabel.visible();
        thrown.expectCause(IsInstanceOf.instanceOf(ElementNotVisibleException.class));
        product.startPage.invisibleButton.visible();
    }

    @Test
    public void testVerifyWindowDoesNotExistByUrl_VerifyWindowDoesNotExistByTitle() {
        product.browser.verifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.verifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void testHasOptions_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionValues, null, WebSelectOption.Value);
        String[] invalidOptionValues = {"0", "1", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testHasOptions_ByText() {
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionTexts, null, WebSelectOption.Text);
        String[] invalidOptions = {"option0", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptions, null, WebSelectOption.Text);
    }

    @Test
    public void testDoesNotHaveOptions_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
        String[] validOptionValues = {"1", "2", "49"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testDoesNotHaveOptions_ByText() {
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending(){
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending(){
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Ascending(){
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Descending(){
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
    }

    @Test
    public void testHasNumberOfOptions(){
        int totalOptions = 5000;
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);
        totalOptions = 50;
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);
    }

    @Test
    public void testHasOptionsInOrder_ByText() {
        String[] validOptions = {"option1", "option2", "option3"};
        product.startPage.dropDown.hasOptionsInOrder(validOptions, WebSelectOption.Text);
        String[] invalidoptions = {"option1", "option4", "option2"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Text);
    }

    @Test
    public void testHasOptionsInOrder_ByValue(){
        String[] validoptions = {"1", "2", "3"};
        product.startPage.dropDown.hasOptionsInOrder(validoptions, WebSelectOption.Value);
        String[] invalidoptions = {"1", "2", "3", "40", "5"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Value);
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect(){
        product.startPage.lexoDropDown.is("apple");
        product.startPage.lexoDropDown.isLike("PPL");
        product.startPage.lexoDropDown.is("01", "value");
        product.startPage.lexoDropDown.isNotLike("appple");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");
        thrown.expectCause(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.startPage.lexoDropDown.is("ZEBRA");
    }

    @Test
    public void testGrids(){
        product.startPage.myGrid.RowBy.index(2).checkBoxButton.click();
        product.startPage.myGrid.RowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.click();
        product.startPage.myGrid.RowBy.material("Laminate").quantity("9").getRow().checkBoxButton.click();
        thrown.expectCause(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.startPage.myGrid.RowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.click();
    }
}
