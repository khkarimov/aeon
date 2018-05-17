package tests;

import aeon.core.common.CompareType;
import aeon.core.common.exceptions.ElementDoesNotHaveNumberOfOptionsException;
import aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import aeon.core.common.exceptions.ElementHasOptionException;
import aeon.core.common.exceptions.ElementsNotInOrderException;
import aeon.core.common.web.WebSelectOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DropdownOptionsTests extends SampleBaseTest{

    @Test
    public void testHasOptions_ByValue() {
        //Arrange
        String[] validOptionValues = {"0", "1", "2", "3"};
        String[] invalidOptionValues = {"0", "1", "fail"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionValues, null, WebSelectOption.Value);

        //Assert
        Assertions.assertThrows(ElementDoesNotHaveOptionException.class,
                () -> product.startPage.dropDown.hasOptions(invalidOptionValues, null, WebSelectOption.Value));
    }

    @Test
    public void testDoesNotHaveOptions_ByValue() {
        //Arrange
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        String[] validOptionValues = {"1", "2", "49"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.Value);

        //Assert
        Assertions.assertThrows(ElementHasOptionException.class,
                () -> product.startPage.dropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.Value));
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending(){
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByValue);

        Assertions.assertThrows(ElementsNotInOrderException.class,
                () -> product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByValue));
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending(){
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByValue);

        Assertions.assertThrows(ElementsNotInOrderException.class,
                () -> product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByValue));
    }

    @Test
    public void testHasOptionsInOrder_ByValue(){
        //Arrange
        String[] validoptions = {"1", "2", "3"};
        String[] invalidoptions = {"1", "2", "3", "40", "5"};

        //Act
        product.startPage.dropDown.hasOptionsInOrder(validoptions, WebSelectOption.Value);

        //Assert
        Assertions.assertThrows(ElementDoesNotHaveOptionException.class,
                () -> product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.Value));
    }

    @Test
    public void testHasNumberOfOptions(){
        //Arrange
        int totalOptions = 5000;
        final int totalOptionsToFail = 50;

        //Act
        product.startPage.dropDown.hasNumberOfOptions(totalOptions);

        //Assert
        Assertions.assertThrows(ElementDoesNotHaveNumberOfOptionsException.class,
                () -> product.startPage.dropDown.hasNumberOfOptions(totalOptionsToFail));
    }
}
