package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.WebSelectOption;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static aeon.core.testabstraction.product.Aeon.launch;
import static aeon.core.testabstraction.product.AeonTestExecution.startTest;

public class GeneralAssertionsTests {
    public static Sample product;

    @Rule
    public TestRule watchMan = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            startTest(description.getMethodName() + "." + description.getClassName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
        }

        @Override
        protected void succeeded(Description description) {
        }
    };


    @Before
    public void beforeTests() {
        product = launch(Sample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @AfterClass
    public static void afterClass() {
        Aeon.done();
    }

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
    public void testNotExists(){
        product.startPage.nonExistentLabel.notExists();
        Assertions.assertThrows(ElementExistsException.class,
                () -> product.startPage.start.notExists());
    }

    @Test
    public void testExists(){
        product.startPage.start.exists();
        Assertions.assertThrows(NoSuchElementException.class,
                () -> product.startPage.nonExistentLabel.exists());
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
        product.startPage.dateSelector.datesApproximatelyEqual("value", DateTime.parse("2016-08-31"), Period.days(0));
        product.startPage.dateSelector.datesApproximatelyEqual("min", DateTime.parse("2016-01-06"), Period.days(5));

        Assertions.assertThrows(DatesNotApproximatelyEqualException.class,
                () -> product.startPage.dateSelector.datesApproximatelyEqual("value", DateTime.parse("2016-08-08"), Period.days(5)));
    }

    @Test
    public void testIs_IsLike_IsNotLike_WithSelect(){
        product.startPage.lexoDropDown.is("apple");
        product.startPage.lexoDropDown.isLike("PPL");
        product.startPage.lexoDropDown.is("01", "value");
        product.startPage.lexoDropDown.isNotLike("appple");
        product.startPage.lexoDropDown.set(WebSelectOption.Text, "zebra");

        Assertions.assertThrows(ValuesAreNotEqualException.class,
                () -> product.startPage.lexoDropDown.is("ZEBRA"));
    }

    @Test
    public void testListGroups(){
        product.startPage.myListGroup.rowBy.description("Need For Speed").getRow().name.is("Aaron Paul");
    }
}
