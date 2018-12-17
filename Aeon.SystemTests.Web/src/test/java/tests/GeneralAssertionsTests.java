package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.WebSelectOption;
import categories.EdgeNotSupported;
import categories.UbuntuTests;
import categories.WindowsTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.Period;

@Category({WindowsTests.class, UbuntuTests.class})
public class GeneralAssertionsTests extends SampleBaseTest {

    @Test
    public void testDisabled() {
        product.startPage.disabledButton.isDisabled();
        Assertions.assertThrows(ElementIsEnabledException.class,
                () -> product.startPage.start.isDisabled());
    }

    @Test
    public void testEnabled() {
        product.startPage.start.isEnabled();
        Assertions.assertThrows(ElementNotEnabledException.class,
                () -> product.startPage.disabledButton.isEnabled());
    }

    @Test
    public void testNotExists() {
        product.startPage.nonExistentLabel.notExists();
        Assertions.assertThrows(ElementExistsException.class,
                () -> product.startPage.start.notExists());
    }

    @Test
    public void testExists() {
        product.startPage.start.exists();
        Assertions.assertThrows(NoSuchElementException.class,
                () -> product.startPage.nonExistentLabel.exists());
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testNotSelected() {
        product.startPage.testCheckbox.notSelected();
        product.startPage.nextRadioButton.notSelected();
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.uncheck();
        product.startPage.testCheckbox.notSelected();
        product.startPage.nextRadioButton.notSelected();
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testSelected() {
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.selected();
        product.startPage.nextRadioButton.check();
        product.startPage.nextRadioButton.selected();
    }

    @Test
    public void testNotVisible() {
        product.startPage.invisibleButton.notVisible();
        Assertions.assertThrows(ElementIsVisibleException.class,
                () -> product.startPage.openAlertButton.notVisible());
    }

    @Test
    public void testVisible() {
        product.startPage.dateLabel.visible();
        Assertions.assertThrows(ElementNotVisibleException.class,
                () -> product.startPage.invisibleButton.visible());
    }

    @Test
    public void testDatesApproximatelyEquals() {
        product.startPage.dateSelector.datesApproximatelyEqual("value", LocalDate.parse("2016-08-31"), Period.ofDays(0));
        product.startPage.dateSelector.datesApproximatelyEqual("min", LocalDate.parse("2016-01-06"), Period.ofDays(5));

        Assertions.assertThrows(DatesNotApproximatelyEqualException.class,
                () -> product.startPage.dateSelector.datesApproximatelyEqual("value", LocalDate.parse("2016-08-08"), Period.ofDays(5)));
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect() {
        product.startPage.lexoDropDown.is("apple");
        product.startPage.lexoDropDown.isLike("PPL");
        product.startPage.lexoDropDown.is("01", "value");
        product.startPage.lexoDropDown.isNotLike("appple");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");

        Assertions.assertThrows(ValuesAreNotEqualException.class,
                () -> product.startPage.lexoDropDown.is("ZEBRA"));
    }

    @Test
    public void testListGroups() {
        product.startPage.myListGroup.rowBy.description("Need For Speed").getRow().name.is("Aaron Paul");
    }
}
