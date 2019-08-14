package tests;

import categories.SafariNotSupported;
import com.ultimatesoftware.aeon.core.common.CompareType;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementDoesNotHaveNumberOfOptionsException;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementDoesNotHaveOptionException;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementHasOptionException;
import com.ultimatesoftware.aeon.core.common.exceptions.ElementsNotInOrderException;
import com.ultimatesoftware.aeon.core.common.web.WebSelectOption;
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
        product.startPage.dropDown.hasOptions(validOptionValues, null, WebSelectOption.VALUE);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptions(invalidOptionValues, null, WebSelectOption.VALUE);
    }

    @Test
    @Category({SafariNotSupported.class})
    public void testDoesNotHaveOptions_ByValue() {
        //Arrange
        String[] invalidOptionValues = {"-1", "h", "Klingon"};
        String[] validOptionValues = {"1", "2", "49"};

        //Act
        product.startPage.dropDown.click();
        product.startPage.dropDown.doesNotHaveOptions(invalidOptionValues, null, WebSelectOption.VALUE);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementHasOptionException.class));
        product.startPage.dropDown.doesNotHaveOptions(validOptionValues, null, WebSelectOption.VALUE);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Ascending() {
        product.startPage.lexoDropDown.hasAllOptionsInOrder(CompareType.ASCENDING_BY_VALUE);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.ASCENDING_BY_VALUE);
    }

    @Test
    public void testHasAllOptionsInOrder_ByValue_Descending() {
        product.startPage.revLexoDropDown.hasAllOptionsInOrder(CompareType.DESCENDING_BY_VALUE);

        thrown.expect(IsInstanceOf.instanceOf(ElementsNotInOrderException.class));
        product.startPage.dropDown.hasAllOptionsInOrder(CompareType.DESCENDING_BY_VALUE);
    }

    @Test
    public void testHasOptionsInOrder_ByValue() {
        //Arrange
        String[] validoptions = {"1", "2", "3"};
        String[] invalidoptions = {"1", "2", "3", "40", "5"};

        //Act
        product.startPage.dropDown.hasOptionsInOrder(validoptions, WebSelectOption.VALUE);

        //Assert
        thrown.expect(IsInstanceOf.instanceOf(ElementDoesNotHaveOptionException.class));
        product.startPage.dropDown.hasOptionsInOrder(invalidoptions, WebSelectOption.VALUE);
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
