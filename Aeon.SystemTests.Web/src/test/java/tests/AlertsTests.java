package tests;

import aeon.core.common.exceptions.NoAlertException;
import aeon.core.common.exceptions.ValuesAreNotAlikeException;
import aeon.core.common.exceptions.ValuesAreNotEqualException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlertsTests extends SampleBaseTest{

    @Test
    public void testAcceptAlert_VerifyAlertExists_VerifyAlertNotExists() {
        product.browser.verifyAlertNotExists();
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.acceptAlert();
    }

    @Test
    public void testSendKeysToAlert_VerifyAlertExists_VerifyAlertNotExists() {
        product.browser.verifyAlertNotExists();
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.sendKeysToAlert("Tester of Alerts");
        product.browser.acceptAlert();
        product.startPage.reactionLabel.is("Tester of Alerts");
    }

    @Test
    public void testDismissAlertWhenThereIsAnAlert() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.dismissAlert();
    }

    @Test
    public void testGetAlertText() {
        //Arrange
        String text;

        //Act
        product.startPage.openAlertButton.click();
        text = product.browser.getAlertText();

        //Assert
        Assertions.assertThrows(NoAlertException.class,
                () -> {
                    product.browser.dismissAlert();
                    product.browser.getAlertText();
                });
        Assertions.assertEquals("Send some keys", text);
    }

    @Test
    public void testVerifyAlertText() {
        Assertions.assertThrows(ValuesAreNotEqualException.class,
                () -> {
                    product.startPage.openAlertButton.click();
                    product.browser.verifyAlertExists();
                    product.browser.verifyAlertText("Send some keys");
                    product.browser.verifyAlertText("Send other keys");
                    product.browser.acceptAlert();
                });
    }

    @Test
    public void testVerifyAlertTextLike() {
        Assertions.assertThrows(ValuesAreNotAlikeException.class,
                () -> {
                    product.startPage.openAlertButton.click();
                    product.browser.verifyAlertExists();
                    product.browser.verifyAlertTextLike("Send some keys", true);
                    product.browser.verifyAlertTextLike("send some keys", true);
                    product.browser.acceptAlert();
                });
    }
}
