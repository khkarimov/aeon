package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.WebSelectOption;
import org.hamcrest.core.IsInstanceOf;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.Test;

public class GeneralAssertionsTests extends SampleBaseTest{

    @Test
    public void testDisabled() {
        product.startPage.disabledButton.isDisabled();
        thrown.expect(IsInstanceOf.instanceOf(ElementIsEnabledException.class));
        product.startPage.start.isDisabled();
    }

    @Test
    public void testEnabled() {
        product.startPage.start.isEnabled();
        thrown.expect(IsInstanceOf.instanceOf(ElementNotEnabledException.class));
        product.startPage.disabledButton.isEnabled();
    }

    @Test
    public void testNotExists(){
        product.startPage.nonExistentLabel.notExists();
        thrown.expect(IsInstanceOf.instanceOf(ElementExistsException.class));
        product.startPage.start.notExists();
    }

    @Test
    public void testExists(){
        product.startPage.start.exists();
        thrown.expect(IsInstanceOf.instanceOf(NoSuchElementException.class));
        product.startPage.nonExistentLabel.exists();
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
        product.startPage.invisibleButton.notVisible();
        thrown.expect(IsInstanceOf.instanceOf(ElementIsVisibleException.class));
        product.startPage.openAlertButton.notVisible();
    }

    @Test
    public void testVisible() {
        product.startPage.dateLabel.visible();
        thrown.expect(IsInstanceOf.instanceOf(ElementNotVisibleException.class));
        product.startPage.invisibleButton.visible();
    }

    @Test
    public void testDatesApproximatelyEquals() {
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-31"), Period.days(0));
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-26"), Period.days(5));
        thrown.expect(IsInstanceOf.instanceOf(DatesNotApproximatelyEqualException.class));
        product.startPage.dateLabel.datesApproximatelyEqual("name", DateTime.parse("2016-08-08"), Period.days(5));
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect(){
        product.startPage.lexoDropDown.is("apple");
        product.startPage.lexoDropDown.isLike("PPL");
        product.startPage.lexoDropDown.is("01", "value");
        product.startPage.lexoDropDown.isNotLike("appple");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");
        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.startPage.lexoDropDown.is("ZEBRA");
    }

    @Test
    public void testListGroups(){
        product.startPage.myListGroup.rowBy.description("Need For Speed").getRow().name.is("Aaron Paul");
    }
}
