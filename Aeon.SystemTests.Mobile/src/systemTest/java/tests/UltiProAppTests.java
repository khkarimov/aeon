package tests;

import aeon.core.common.web.BrowserType;
import aeon.selenium.SeleniumConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ultiproapp.UltiProApp;

import static aeon.core.testabstraction.product.Aeon.launch;

public class UltiProAppTests {

    private UltiProApp ultiProApp;

    @Before
    public void setUp() {
        ultiProApp = launch(UltiProApp.class);

        // iOS does not support app cache cleaning
        String appPath = ultiProApp.getConfig(SeleniumConfiguration.Keys.APP, "");
        if (ultiProApp.browser.getBrowserType() == BrowserType.IOSHybridApp && appPath.isEmpty()) {
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
