package tests;

import aeon.core.common.exceptions.*;
import aeon.core.common.web.BrowserSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowserWindowTests extends SampleBaseTest{

    @Test
    public void testGetElementAttributes() {
        String classAttribute = product.startPage.formTextBox.getElementAttribute("class").toString();
        Assertions.assertEquals("mdl-textfield__input", classAttribute);
    }

    @Test
    public void testVerifyTitle() {
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(ValuesAreNotEqualException.class,
                () -> {
                    product.browser.verifyTitle("Material Design Lite");
                    product.browser.verifyTitle("Fake Title");
                });

        //Assert
        Assertions.assertTrue(exception instanceof ValuesAreNotEqualException);
    }

    @Test
    public void testVerifyURL() {
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(ValuesAreNotEqualException.class,
                () -> {
                    product.browser.goToUrl("https://ci.mia.ucloud.int/login.html");
                    product.browser.verifyURL("https://ci.mia.ucloud.int/login.html");
                    product.browser.verifyURL("http://www.espne.com/");
                });

        //Assert
        Assertions.assertTrue(exception instanceof ValuesAreNotEqualException);
    }
    @Test
    public void testVerifyWindowDoesNotExistByUrlVerifyWindowDoesNotExistByTitle() {
        product.browser.verifyWindowDoesNotExistByTitle("fakeTitle");
        product.browser.verifyWindowDoesNotExistByUrl("fakeUrl");
    }

    @Test
    public void testGrids(){
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(NoSuchElementsException.class,
                () -> {
                    product.startPage.myGrid.rowBy.index(2).checkBoxButton.click();
                    product.startPage.myGrid.rowBy.material("Laminate").unitPrice("9").getRow().checkBoxButton.click();
                    product.startPage.myGrid.rowBy.material("Laminate").quantity("9").getRow().checkBoxButton.click();
                    product.startPage.myGrid.rowBy.material("Laminate").quantity("9").getRow().unitPrice.is("$2.35");
                    product.startPage.myGrid.rowBy.material("Acrylic").getRow().exists();
                    product.startPage.myGrid.rowBy.material("Acrylic").quantity("9").getRow().checkBoxButton.click();
                });

        //Assert
        Assertions.assertTrue(exception instanceof NoSuchElementsException);
    }

    @Test
    public void testSwitchToMainWindow() {
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(NotAllPopupWindowsClosedException.class,
                () -> {
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
                    product.browser.switchToMainWindow(true);
                });

        //Assert
        Assertions.assertTrue(exception instanceof NotAllPopupWindowsClosedException);
    }

    @Test
    public void testSwitchToWindowByTitle() {
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(NoSuchWindowException.class,
                () -> {
                    product.browser.verifyTitle("Material Design Lite");
                    product.startPage.popupButton.click();
                    product.browser.switchToWindowByTitle("Google");
                    product.browser.verifyTitle("Google");
                    product.browser.switchToWindowByTitle("Some Fake Title");
                });

        //Assert
        Assertions.assertTrue(exception instanceof NoSuchWindowException);
    }

    @Test
    public void testSwitchToWindowByUrl() {
        //Arrange
        Throwable exception;

        //Act
        exception = assertThrows(NoSuchWindowException.class,
                () -> {
                    product.browser.verifyTitle("Material Design Lite");
                    product.startPage.popupButton.click();
                    product.browser.switchToWindowByUrl("https://www.google.com");
                    product.browser.verifyTitle("Google");
                    product.browser.switchToWindowByUrl("www.fake.com");
                });

        //Assert
        Assertions.assertTrue(exception instanceof NoSuchWindowException);
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
