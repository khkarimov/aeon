package tests;

import aeon.core.common.CompareType;
import aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import aeon.core.common.exceptions.ElementHasOptionException;
import aeon.core.common.exceptions.ElementsNotInOrderException;
import aeon.core.common.web.WebSelectOption;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

public class DropdownOptionsByTextTests extends SampleBaseTest {

    @Test
    public void testHasOptions_ByText() {
        //Arrange
        String[] validOptionTexts = {"option0", "option1", "option2", "option3"};
        String[] invalidOptions = {"option0", "fail"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.hasOptions(validOptionTexts, null, WebSelectOption.Text);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptions, null, WebSelectOption.Text);
    }

    @Test
    public void testDoesNotHaveOptions_ByText() {
        //Arrange
        String[] invalidOptionTexts = {"Option-1", "Klingon", "nothing"};
        String[] validOptionTexts = {"Option-1", "Klingon", "nothing", "option1"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionTexts, null, WebSelectOption.Text);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionTexts, null, WebSelectOption.Text);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Ascending(){
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.AscendingByText);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.AscendingByText);
    }

    @Test
    public void testHasOptionsInOrder_ByText() {
        //Arrange
        String[] validOptions = {"option1", "option2", "option3"};
        String[] invalidOptions = {"option1", "option4", "option2"};

        //Act
        product.startPage.dropDown.hasOptionsInOrder(validOptions, WebSelectOption.Text);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidOptions, WebSelectOption.Text);
    }

    @Test
    public void testHasAllOptionsInOrder_ByText_Descending(){
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DescendingByText);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DescendingByText);
    }
}
