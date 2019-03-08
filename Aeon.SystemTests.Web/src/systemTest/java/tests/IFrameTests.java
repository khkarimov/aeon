package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.iframe.IFrameWikiSample;
import main.iframe.NestedIFrameWikiSample;
import main.iframe.pages.NestedIFramePage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working on a UltiPro environment and to test the switch mechanism.
 */
public class IFrameTests {
    private static IFrameWikiSample wikiPage;
    private static NestedIFrameWikiSample nestedWikiPage;

    @Before
    public void beforeIFrameTests() {
        wikiPage = launch(IFrameWikiSample.class);
        String environment = wikiPage.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/iFrame.html");
        String protocol = wikiPage.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        wikiPage.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterIFrameTests() {
        wikiPage.browser.quit();
    }

    @Test
    public void iFrameWithSwitchTest(){
        wikiPage.iFramePage.wikiSearchTextBox.click();
        wikiPage.iFramePage.searchButton.click();
        wikiPage.iFramePage.wikiSearchTextBox.click();
        wikiPage.iFramePage.wikiSearchTextBox.set("Ultimate Software");
        wikiPage.iFramePage.searchButton.click();
        wikiPage.iFramePage.wikiLogo.click();
        wikiPage.iFramePage.popupButton.click();
        wikiPage.browser.switchToWindowByTitle("Bing");
        wikiPage.browser.verifyTitle("Bing");
        wikiPage.browser.switchToWindowByTitle("Title");
        wikiPage.browser.verifyTitle("Title");
    }
}

