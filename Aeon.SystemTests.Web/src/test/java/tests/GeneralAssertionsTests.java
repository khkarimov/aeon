package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.WebSelectOption;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneralAssertionsTests extends SampleBaseTest{

    @Test
    public void testDisabled() {
        Assertions.assertThrows(ElementIsEnabledException.class,
                () -> {
                    product.startPage.disabledButton.isDisabled();
                    product.startPage.start.isDisabled();
                });
    }

    @Test
    public void testEnabled() {
        Assertions.assertThrows(ElementNotEnabledException.class,
                () -> {
                    product.startPage.start.isEnabled();
                    product.startPage.disabledButton.isEnabled();
                });
    }

    @Test
    public void testNotExists(){
        Assertions.assertThrows(ElementExistsException.class,
                () -> {
                    product.startPage.nonExistentLabel.notExists();
                    product.startPage.start.notExists();
                });
    }

    @Test
    public void testExists(){
        Assertions.assertThrows(NoSuchElementException.class,
                () -> {
                    product.startPage.start.exists();
                    product.startPage.nonExistentLabel.exists();
                });
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
    public void testSelected() {
        product.startPage.testCheckbox.check();
        product.startPage.testCheckbox.selected();
        product.startPage.nextRadioButton.check();
        product.startPage.nextRadioButton.selected();
    }

    @Test
    public void testNotVisible() {
        Assertions.assertThrows(ElementIsVisibleException.class,
                () -> {
                    product.startPage.invisibleButton.notVisible();
                    product.startPage.openAlertButton.notVisible();
                });
    }

    @Test
    public void testVisible() {
        Assertions.assertThrows(ElementNotVisibleException.class,
                () -> {
                    product.startPage.dateLabel.visible();
                    product.startPage.invisibleButton.visible();
                });
    }

    @Test
    public void testDatesApproximatelyEquals() {
        Assertions.assertThrows(DatesNotApproximatelyEqualException.class,
                () -> {
                    product.startPage.dateSelector.datesApproximatelyEqual("value", DateTime.parse("2016-08-31"), Period.days(0));
                    product.startPage.dateSelector.datesApproximatelyEqual("min", DateTime.parse("2016-01-06"), Period.days(5));
                    product.startPage.dateSelector.datesApproximatelyEqual("value", DateTime.parse("2016-08-08"), Period.days(5));
                });
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect(){
        Assertions.assertThrows(ValuesAreNotEqualException.class,
                () -> {
                    product.startPage.lexoDropDown.is("apple");
                    product.startPage.lexoDropDown.isLike("PPL");
                    product.startPage.lexoDropDown.is("01", "value");
                    product.startPage.lexoDropDown.isNotLike("appple");
                    product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");
                    product.startPage.lexoDropDown.is("ZEBRA");
                });
    }

    @Test
    public void testListGroups(){
        product.startPage.myListGroup.rowBy.description("Need For Speed").getRow().name.is("Aaron Paul");
    }
}
