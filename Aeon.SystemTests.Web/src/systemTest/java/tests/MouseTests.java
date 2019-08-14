package tests;

import categories.SafariNotSupported;
import categories.UbuntuTests;
import categories.WindowsTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category({WindowsTests.class, UbuntuTests.class})
public class MouseTests extends SampleBaseTest {

    @Test
    @Category({SafariNotSupported.class})
    public void testClickAndHold() {
        product.startPage.dateLabel.clickAndHold(5000);
        product.startPage.reactionLabel.is("mouse held for 5000");
    }

    @Test
    @Category({SafariNotSupported.class})
    public void testDoubleClick() {
        product.startPage.ultimateLogoImage.doubleClick();
        product.startPage.ultimateLogoImageDoubleClick.visible();
        //the ultimate logo should appear in the image element "dbl-click-image"
    }

    @Test
    @Category({SafariNotSupported.class})
    public void testDragAndDrop() {
        product.browser.goToUrl("http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html");
        product.startPage.draggedListItem.notExists();
        product.startPage.draggableListItem.dragAndDrop(product.startPage.dropTarget);
        product.startPage.draggedListItem.exists();
    }

    @Test
    @Category({SafariNotSupported.class})
    public void testDragAndDropHTML5() {
        product.browser.goToUrl("https://www.w3schools.com/html/html5_draganddrop.asp");
        product.startPage.draggedHTML5Item.notExists();
        product.startPage.draggableHTML5Item.dragAndDrop(product.startPage.html5DropTarget);
        product.startPage.draggedHTML5Item.exists();
    }

    @Test
    @Category({SafariNotSupported.class})
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
