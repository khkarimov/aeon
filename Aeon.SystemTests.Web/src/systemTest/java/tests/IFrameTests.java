package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.iframe.IFrameWikiSample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working on a UltiPro environment and to test the switch mechanism.
 */
public class IFrameTests {
    private static IFrameWikiSample product;

    @Before
    public void beforeTests() {
        product = launch(IFrameWikiSample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/iFrame_clean.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }

    @Test
    public void iFrameWithSwitchTest(){
        product.iFramePage.wikiSearchTextBox.click();
        product.iFramePage.searchButton.click();
        product.iFramePage.wikiSearchTextBox.click();
        product.iFramePage.wikiSearchTextBox.set("Ultimate Software");
        product.iFramePage.searchButton.click();
        product.iFramePage.wikiLogo.click();
        product.iFramePage.popupButton.click();
        product.browser.switchToWindowByTitle("Bing");
        product.browser.verifyTitle("Bing");
        product.browser.switchToWindowByTitle("Title");
        product.browser.verifyTitle("Title");
    }
}

