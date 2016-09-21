import echo.core.common.CompareType;
import echo.core.common.exceptions.ElementDoesNotHaveNumberOfOptionsException;
import echo.core.common.exceptions.ElementDoesNotHaveOptionException;
import echo.core.common.exceptions.ElementHasOptionException;
import echo.core.common.exceptions.ElementsNotInOrderException;
import echo.core.common.web.BrowserSize;
import echo.core.common.web.BrowserType;
import echo.core.common.web.WebSelectOption;
import echo.core.common.web.selectors.By;
import main.Sample;
import org.hamcrest.core.IsInstanceOf;
import org.junit.*;
import org.junit.rules.ExpectedException;

import javax.management.InstanceNotFoundException;

import static echo.core.test_abstraction.product.Echo.Launch;

@Ignore
public class OptionsCommandsTesting {
    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    //region Test HasOptionsCommand
    @Test
    public void TestHasOptions_Success_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestHasOptions_Fail_ByValue(){
        String[] invalidOptionValues = {"0", "1", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestHasOptions_Success_ByText() {
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.HasOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void TestHasOptions_Fail_ByText(){
        String[] invalidOptions = {"option0", "fail"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptions(invalidOptions, null, WebSelectOption.Text);
    }

    //endregion

    //region Test DoesNotHaveOptionsCommand
    @Test
    public void TestDoesNotHaveOptions_Success_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestDoesNotHaveOptions_Fail_ByValue(){
        String[] validOptionValues = {"1", "2", "49"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.DoesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void TestDoesNotHaveOptions_Success_ByText() {
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        product.StartPage.DropDown.Click();
        product.StartPage.DropDown.DoesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void TestDoesNotHaveOptions_Fail_ByText() {
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.StartPage.DropDown.DoesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
    }
    //endregion

    //region Test HasAllOptionsInOrder
    @Test
    public void TestHasAllOptionsInOrder_Success_ByValue_Ascending(){
        product.StartPage.LexoDropDown.HasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_Fail_ByValue_Ascending(){
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_Success_ByValue_Descending(){
        product.StartPage.RevLexoDropDown.HasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_Fail_ByValue_Descending(){
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void TestHasAllOptionsInOrder_Success_ByText_Ascending(){
        product.StartPage.LexoDropDown.HasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void TestHasAllOptionsInOrder_Fail_ByText_Ascending(){
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void TestHasAllOptionsInOrder_Success_ByText_Descending(){
        product.StartPage.RevLexoDropDown.HasAllOptionsInOrder(CompareType.DescendingByText);
    }

    @Test
    public void TestHasAllOptionsInOrder_Fail_ByText_Descending(){
        thrown.expectCause(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.StartPage.DropDown.HasAllOptionsInOrder(CompareType.DescendingByText);
    }

    //endregion

    //region Test HasNumberOfOptions
    @Test
    public void TestHasNumberOfOptions_Success(){
        int totalOptions = 5000;
        product.StartPage.DropDown.HasNumberOfOptions(totalOptions);
    }

    @Test
    public void TestHasNumberOfOptions_Fail(){
        // we should test against more numbers or maybe mock the number
        int totalOptions = 50;
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.StartPage.DropDown.HasNumberOfOptions(totalOptions);
    }
    //endregion

    //region Test HasOptionsInOrder
    @Test
    public void TestHasOptionsInOrder_Success_ByText(){
        String[] validOptions = {"option1", "option2", "option3"};
        product.StartPage.DropDown.HasOptionsInOrder(validOptions, WebSelectOption.Text);
    }

    @Test
    public void TestHasOptionsInOrder_Fail_ByText(){
        String[] invalidoptions = {"option1", "option4", "option2"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptionsInOrder(invalidoptions, WebSelectOption.Text);
    }

    @Test
    public void TestHasOptionsInOrder_Success_ByValue(){
        String[] validoptions = {"1", "2", "3"};
        product.StartPage.DropDown.HasOptionsInOrder(validoptions, WebSelectOption.Value);
    }

    @Test
    public void TestHasOptionsInOrder_Fail_ByValue(){
        String[] validoptions = {"1", "2", "3", "40", "5"};
        thrown.expectCause(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.StartPage.DropDown.HasOptionsInOrder(validoptions, WebSelectOption.Value);
    }
    //endregion
//
//    //The Following Test Methods are dependent on an UltiPro Environment, and have not been converted to use our Sample Page.
//
//
////    @Test
////    public void TestVisibleAndNotVisible_05() {
////        product.Login.WarningMessage.NotVisible();
////        product.Login.UserNameTextBox.Set("usa-canu");
////        product.Login.PasswordTextBox.Set("FAIL");
////        product.Login.LoginButton.Click();
////        product.Login.WarningMessage.Visible();
////    }
////
////
////    @Test
////    public void TestGetElementAttributeWithTextBoxNameAttribute() {
////        String testAttributeValues = product.Login.UserNameTextBox.GetElementAttribute("name").toString();
////        System.out.println("User Name text box name value: " + testAttributeValues);
////    }
////
////    @Test
////    public void TestHasElementsInOrder() {
////        String[] options = new String[]{"English (USA)", "Italiano (ITA)", "Melayu (MYS)"};
////        String[] values = new String[]{"0", "5", "13"};
////        String[] badValues = new String[]{"0", "13", "5"};
////        String[] badOptions = new String[]{"English (USA)", "Melayu (MYS)", "Italiano (ITA)"};
////        String[] value = new String[]{"0"};
////        String[] option = new String[]{"English (USA)"};
////        product.Login.LanguageSelect.HasOptionsInOrder(options, WebSelectOption.Text);
////        product.Login.LanguageSelect.HasOptionsInOrder(values, WebSelectOption.Value);
////        product.Login.LanguageSelect.HasOptionsInOrder(value, WebSelectOption.Value);
////        product.Login.LanguageSelect.HasOptionsInOrder(option, WebSelectOption.Text);
////    }
////
////    @Test
////    public void TestHas() {
////        String[] texts = {"English (USA)", "English (GBR)", "English (CAN)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Melayu (MYS)"
////                , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
////        String[] notMessages = new String[]{"asdasdasd", "sss"};
////        String[] values = new String[]{"0", "5"};
////        String[] badValues = new String[]{"13s"};
////        product.Login.LanguageSelect.Has(texts, "option");
////        product.Login.LanguageSelect.DoesNotHave(notMessages, "option");
////        product.Login.LanguageSelect.DoesNotHave(badValues, "option", "value");
////        product.Login.LanguageSelect.Has(values, "option", "value");
////        product.Login.LanguageSelect.HasOnly(texts, "option");
////        product.Login.LanguageSelect.HasOnly(new String[]{"1"}, "option[value='1']", "value");
////        product.Login.PasswordTextBox.Is("ctl00_Content_Login1_Password", "id");
////    }
//
////    @Test
////    public void TestGoBackGoForward_01() {
////        String[] texts = {"English (USA)", "Italiano (ITA)", "Français (CAN)", "Español (USA)", "Português (BRA)", "Deutsch (DEU)", "Nederlands (NLD)", "Français (FRA)", "Italiano (ITA)", "Melayu (MYS)"
////                , "Pilipino (PHL)", "Dansk (DNK)", "Svenska (SWE)"};
////        String[] shouldfail = {"Klingon", "African Clicky Noises", "Reptilian Hissing"};
////        String[] values = {"1", "2"};
////        String[] valuesShouldFail = {"-12", "Blue"};
////        product.Login.LanguageSelect.Click();
////        product.Login.LanguageSelect.HasOptions(texts, null, WebSelectOption.Text);
////        product.Login.LanguageSelect.HasOptions(values, null, WebSelectOption.Value);
////        product.Login.LanguageSelect.DoesNotHaveOptions(shouldfail, null, WebSelectOption.Text);
////        product.Login.LanguageSelect.DoesNotHaveOptions(valuesShouldFail, null, WebSelectOption.Value);
////        product.Login.LoginButton.Click();
////        product.browser.GoBack();
////        product.browser.GoForward();
////        //product.Home.ViewPayStatement.Click();
////        //System.out.println("After Click");
////    }
//
//
}
