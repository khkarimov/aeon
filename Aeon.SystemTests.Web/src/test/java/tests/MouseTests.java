package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;
import org.junit.*;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static aeon.core.testabstraction.product.Aeon.launch;
import static aeon.core.testabstraction.product.AeonTestExecution.startTest;
import static aeon.core.testabstraction.product.AeonTestExecution.testFailed;
import static aeon.core.testabstraction.product.AeonTestExecution.testSucceeded;

public class MouseTests {
    public static Sample product;

    @Rule
    public TestRule watchMan = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            startTest(description.getMethodName() + "." + description.getClassName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            testFailed(e.getMessage());
        }

        @Override
        protected void succeeded(Description description) {
            testSucceeded();
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
    public void testClickAndHold() {
        product.startPage.dateLabel.clickAndHold(5000);
        product.startPage.reactionLabel.is("mouse held for 5000");
    }

    @Test
    public void testDoubleClick() {
        product.startPage.ultimateLogoImage.doubleClick();
        product.startPage.ultimateLogoImageDoubleClick.visible();
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void testDragAndDrop() {
        product.browser.goToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.startPage.draggedListItem.notExists();
        product.startPage.draggableListItem.dragAndDrop("ul[id='box2']");
        product.startPage.draggedListItem.exists();
    }

    @Test
    public void testRightClick() {
        product.startPage.dateLabel.rightClick();
        product.startPage.reactionLabel.is("right click");
    }

    @Test
    public void testMouseOver() {
        product.startPage.ultimateLogoImage.mouseOver();
        product.startPage.heatLogoImage.exists();
        product.browser.refresh();
    }

    @Test
    public void testMouseOver_MouseOut_Refresh() {
        product.startPage.start.mouseOver();
        product.startPage.start.mouseOut();
        product.browser.refresh();
    }
}
