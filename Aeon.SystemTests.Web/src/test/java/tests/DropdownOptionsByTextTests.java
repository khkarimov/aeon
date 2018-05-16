package tests;

import aeon.core.common.CompareType;
import aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import aeon.core.common.exceptions.ElementHasOptionException;
import aeon.core.common.exceptions.ElementsNotInOrderException;
import aeon.core.common.web.WebSelectOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DropdownOptionsByTextTests extends SampleBaseTest {

    @Test
    public void testHasOptions_ByText() {
        //Arrange
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        String[] invalidOptions = {"option0", "fail"};

        //Act
        //Assert
        Assertions.assertThrows(ElementDoesNotHaveOptionException.class,
                () -> {
                    product.startPage.dropDown.click();
                    product.startPage.dropDown.hasOptions(validOptionTexts, null, WebSelectOption.Text);
                    product.startPage.dropDown.hasOptions(invalidOptions, null, WebSelectOption.Text);
                });
    }

    @Test
    public void testDoesNotHaveOptions_ByText() {
        //Arrange
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};

        //Act
        //Assert
        Assertions.assertThrows(ElementHasOptionException.class,
                () -> {
                    product.startPage.dropDown.click();
                    product.startPage.dropDown.doesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);
                    product.startPage.dropDown.doesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
                });
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Ascending(){
        Assertions.assertThrows(ElementsNotInOrderException.class,
                () -> {
                    product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
                    product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
                });
    }

    @Test
    public void testHasOptionsInOrder_ByText() {
        //Arrange
        String[] validOptions = {"option1", "option2", "option3"};
        String[] invalidOptions = {"option1", "option4", "option2"};

        //Act
        //Assert
        Assertions.assertThrows(ElementDoesNotHaveOptionException.class,
                () -> {
                    product.startPage.dropDown.hasOptionsInOrder(validOptions, WebSelectOption.Text);
                    product.startPage.dropDown.hasOptionsInOrder(invalidOptions, WebSelectOption.Text);
                });
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Descending(){
        Assertions.assertThrows(ElementsNotInOrderException.class,
                () -> {
                    product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
                    product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
                });
    }

}
