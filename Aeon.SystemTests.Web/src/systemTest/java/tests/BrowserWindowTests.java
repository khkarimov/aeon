package tests;

import categories.EdgeNotSupported;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchElementsException;
import com.ultimatesoftware.aeon.core.common.exceptions.NoSuchWindowException;
import com.ultimatesoftware.aeon.core.common.exceptions.NotAllPopupWindowsClosedException;
import com.ultimatesoftware.aeon.core.common.exceptions.ValuesAreNotEqualException;
import com.ultimatesoftware.aeon.core.common.web.BrowserSize;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BrowserWindowTests extends SampleBaseTest {

    @Test
    public void testVerifyTitle() {
        product.browser.verifyTitle("Material Design Lite");

        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyTitle("Fake Title");
    }

    @Test
    public void testVerifyURL() {
        product.browser.goToUrl("https://www.bing.com/");
        product.browser.verifyURL("https://www.bing.com/");

        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyURL("http://www.espne.com/");
    }

    @Test
    public void testVerifyWindowDoesNotExistByUrlVerifyWindowDoesNotExistByTitle() {
        product.browser.verifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.verifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void testGrids() {
        product.startPage.materialTableContainer.rowBy.index(2).checkBoxButton.click();
        product.startPage.materialTableContainer.rowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.click();
        product.startPage.materialTableContainer.rowBy.material("Laminate").quantity("9").getRow().checkBoxButton.click();
        product.startPage.materialTableContainer.rowBy.material("Laminate").quantity("9").getRow().unitPrice.is("$2.35");
        product.startPage.materialTableContainer.rowBy.material("Acrylic").getRow().exists();

        thrown.expect(IsInstanceOf.instanceOf(NoSuchElementsException.class));
        product.startPage.materialTableContainer.rowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.click();
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testSwitchToMainWindow() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Bing");
        product.browser.verifyTitle("Bing");
        product.browser.switchToMainWindow();
        product.browser.verifyTitle("Material Design Lite");
        product.browser.switchToWindowByTitle("Bing");
        product.browser.close();
        product.browser.switchToMainWindow(true);
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Bing");

        thrown.expect(IsInstanceOf.instanceOf(NotAllPopupWindowsClosedException.class));
        product.browser.switchToMainWindow(true);
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testSwitchToWindowByTitle() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByTitle("Bing");
        product.browser.verifyTitle("Bing");

        thrown.expect(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByTitle("Some Fake Title");
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testSwitchToWindowByUrl() {
        product.browser.verifyTitle("Material Design Lite");
        product.startPage.popupButton.click();
        product.browser.switchToWindowByUrl("https://www.bing.com");
        product.browser.verifyTitle("Bing");

        thrown.expect(IsInstanceOf.instanceOf(NoSuchWindowException.class));
        product.browser.switchToWindowByUrl("www.fake.com");
    }

    @Category({EdgeNotSupported.class})
    @Test
    public void testWindowResizing_GoBack_GoForward_ScrollToEnd_ScrollToTop() {
        product.browser.resize(BrowserSize.TABLET_LANDSCAPE);
        product.browser.resize(BrowserSize.SMALL_TABLET_LANDSCAPE);
        product.browser.resize(BrowserSize.MOBILE_LANDSCAPE);
        product.browser.maximize();
        product.browser.goToUrl("https://en.wikipedia.org");
        product.browser.scrollToEnd();
        product.browser.scrollToTop();
        product.browser.goBack();
        product.browser.goForward();
    }
}
