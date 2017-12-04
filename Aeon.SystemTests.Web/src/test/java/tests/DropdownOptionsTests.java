package tests;

import aeon.core.common.CompareType;
import aeon.core.common.exceptions.ElementDoesNotHaveNumberOfOptionsException;
import aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import aeon.core.common.exceptions.ElementHasOptionException;
import aeon.core.common.exceptions.ElementsNotInOrderException;
import aeon.core.common.web.WebSelectOption;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

public class DropdownOptionsTests extends SampleBaseTest{

    @Test
    public void testHasOptions_ByValue() {
        String[] validOptionValues = {"0", "1", "2", "3"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionValues, null, WebSelectOption.Value);
        String[] invalidOptionValues = {"0", "1", "fail"};
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testDoesNotHaveOptions_ByValue() {
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);
        String[] validOptionValues = {"1", "2", "49"};
        thrown.expect(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending(){
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending(){
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void testHasOptionsInOrder_ByValue(){
        String[] validoptions = {"1", "2", "3"};
        product.startPage.dropDown.hasOptionsInOrder(validoptions, WebSelectOption.Value);
        String[] invalidoptions = {"1", "2", "3", "40", "5"};
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Value);
    }

    @Test
    public void testHasNumberOfOptions(){
        int totalOptions = 5000;
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);
        totalOptions = 50;
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);
    }
}
