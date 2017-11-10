package tests;


import aeon.core.common.exceptions.NoAlertException;
import aeon.core.common.exceptions.ValuesAreNotAlikeException;
import aeon.core.common.exceptions.ValuesAreNotEqualException;
import categories.UbuntuTests;
import categories.WindowsTests;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.junit.experimental.categories.Category;


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
        product.startPage.openAlertButton.click();
        String text = product.browser.getAlertText();

        assert(text.equals("Send some keys"));
        product.browser.dismissAlert();
        thrown.expect(IsInstanceOf.instanceOf(NoAlertException.class));
        product.browser.getAlertText();
    }

    @Test
    public void testVerifyAlertText() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertText("Send some keys");
        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotEqualException.class));
        product.browser.verifyAlertText("Send other keys");
        product.browser.acceptAlert();
    }

    @Test
    public void testVerifyAlertTextLike() {
        product.startPage.openAlertButton.click();
        product.browser.verifyAlertExists();
        product.browser.verifyAlertTextLike("Send some keys", true);
        thrown.expect(IsInstanceOf.instanceOf(ValuesAreNotAlikeException.class));
        product.browser.verifyAlertTextLike("send some keys", true);
        product.browser.acceptAlert();
    }
}
