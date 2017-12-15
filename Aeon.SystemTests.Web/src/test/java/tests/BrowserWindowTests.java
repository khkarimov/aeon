package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserSize;
import org.hamcrest.core.IsInstanceOf;
import org.junit.*;

public class BrowserWindowTests extends SampleBaseTest{

    @Test
    public void testGetElementAttributes() {
        String classAttribute = product.startPage.formTextBox.getElementAttribute("class").toString();
        assert (classAttribute.equals("mdl-textfield__input"));
    }

    @Test
    public void testVerifyTitle() {
        product.browser.verifyTitle("Material Design Lite");
        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyTitle("Fake Title");
    }

    @Test
    public void testVerifyURL() {
        product.browser.goToUrl("https://ci.mia.ucloud.int/login.html");
        product.browser.verifyURL("https://ci.mia.ucloud.int/login.html");
        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyURL("http://www.espne.com/");
    }
    @Test
    public void testVerifyWindowDoesNotExistByUrlVerifyWindowDoesNotExistByTitle() {
        product.browser.verifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.verifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void testGrids(){
        product.startPage.myGrid.rowBy.index(2).checkBoxButton.click();
        product.startPage.myGrid.rowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.click();
        product.startPage.myGrid.rowBy.material("Laminate").quantity("9").getRow().checkBoxButton.click();
        product.startPage.myGrid.rowBy.material("Laminate").quantity("9").getRow().unitPrice.is("$2.35");
        product.startPage.myGrid.rowBy.material("Acrylic").getRow().exists();
        thrown.expect(IsInstanceOf.instanceOf(NoSuchElementsException.class));
        product.startPage.myGrid.rowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.click();
    }

    @Test
    public void testSwitchToMainWindow() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        product.browser.switchToMainWindow();
        product.browser.verifyTitle("Material Design Lite");
        product.browser.switchToWindowByTitle("Google");
        product.browser.close();
        product.browser.switchToMainWindow(true);
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        thrown.expect(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.switchToMainWindow(true);
    }

    @Test
    public void testSwitchToWindowByTitle() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Google");
        product.browser.verifyTitle("Google");
        thrown.expect(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByTitle("Some Fake Title");
    }

    @Test
    public void testSwitchToWindowByUrl() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByUrl("https://www.google.com");
        product.browser.verifyTitle("Google");
        thrown.expect(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByUrl("www.fake.com");
    }

    @Test
    public void testWindowResizing_GoBack_GoForward_ScrollToEnd_ScrollToTop() {
        product.browser.resize(BrowserSize.TabletLandscape);
        product.browser.resize(BrowserSize.SmallTabletLandscape);
        product.browser.resize(BrowserSize.MobileLandscape);
        product.browser.maximize();
        product.browser.goToUrl("https://ci.mia.ucloud.int");
        product.browser.scrollToEnd();
        product.browser.scrollToTop();
        product.browser.goBack();
        product.browser.goForward();
    }
}
