package tests;

import org.junit.Test;


public class MouseTests extends SampleBaseTest {
    @Test
    public void testClickAndHold() {
        product.startPage.dateLabel.clickAndHold(5000);
    }

    @Test
    public void testDoubleClick() {
        product.startPage.ultimateLogoImage.doubleClick();
        String src = product.startPage.ultimateLogoImageDoubleClick.getElementAttribute("src").toString();
        assert(src.contains("ultimate-image.png"));
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    public void testDragAndDrop() {
        product.browser.goToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.startPage.draggableListItem.dragAndDrop("ul[id='box2']");
    }

    @Test
    public void testRightClick() {
        product.startPage.dateLabel.rightClick();
        String validationText = product.startPage.reactionLabel.getElementAttribute("textContent").toString();
        assert(validationText.equals("right click"));
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
