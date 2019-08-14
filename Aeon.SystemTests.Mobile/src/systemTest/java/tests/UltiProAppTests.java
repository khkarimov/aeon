package tests;

import com.ultimatesoftware.aeon.core.common.mobile.AppType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ultiproapp.UltiProApp;

import static com.ultimatesoftware.aeon.core.testabstraction.product.Aeon.launch;

public class UltiProAppTests {

    private UltiProApp ultiProApp;

    @Before
    public void setUp() {
        ultiProApp = launch(UltiProApp.class);

        // iOS does not support app cache cleaning
        String appPath = ultiProApp.getConfig("aeon.appium.app", "");
        if (ultiProApp.browser.getBrowserType() == AppType.IOS_HYBRID_APP && appPath.isEmpty()) {
            try {
                ultiProApp.iOSIdentitySignInPage.doneButton.click();
                ultiProApp.loginPage.clearAccessCodeButton.click();
            } catch (Exception e) {
                // Don't fail if the clean up fails
            }
        }
    }

    @After
    public void tearDown() {
        ultiProApp.mobileDevice.quit();
    }

    @Test
    public void AppLaunchAndInteractionTest() {
        ultiProApp.accessCodePage.accessCodeInputField.visible();
        ultiProApp.accessCodePage.accessCodeInputField.set("Test");
        ultiProApp.accessCodePage.accessCodeInputField.is("Test", "value");
        ultiProApp.accessCodePage.whatIsACompanyAccessCode.visible();
        ultiProApp.accessCodePage.whatIsACompanyAccessCode.is("What is a Company Access Code?");
        ultiProApp.accessCodePage.supportLink.visible();
        ultiProApp.accessCodePage.supportLink.is("Support");
    }
}
