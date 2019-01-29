package tests;

import aeon.core.common.CompareType;
import aeon.core.common.exceptions.ElementDoesNotHaveNumberOfOptionsException;
import aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import aeon.core.common.exceptions.ElementHasOptionException;
import aeon.core.common.exceptions.ElementsNotInOrderException;
import aeon.core.common.web.WebSelectOption;
import categories.SafariNotSupported;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DropdownOptionsTests extends SampleBaseTest {

    @Test
    @Category({SafariNotSupported.class})
    public void testHasOptions_ByValue() {
        //Arrange
        String[] validOptionValues = {"0", "1", "2", "3"};
        String[] invalidOptionValues = {"0", "1", "fail"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionValues, null, WebSelectOption.Value);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptionValues, null, WebSelectOption.Value);
    }

    @Test
    @Category({SafariNotSupported.class})
    public void testDoesNotHaveOptions_ByValue() {
        //Arrange
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        String[] validOptionValues = {"1", "2", "49"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.Value);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending() {
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending() {
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);
    }

    @Test
    public void testHasOptionsInOrder_ByValue() {
        //Arrange
        String[] validoptions = {"1", "2", "3"};
        String[] invalidoptions = {"1", "2", "3", "40", "5"};

        //Act
        product.startPage.dropDown.hasOptionsInOrder(validoptions, WebSelectOption.Value);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Value);
    }

    @Test
    public void testHasNumberOfOptions() {
        //Arrange
        int totalOptions = 500;
        final int totalOptionsToFail = 50;

        //Act
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveNumberOfOptionsException.class));
        product.startPage.dropDown.hasNumberOfOptions(totalOptionsToFail);
    }
}
