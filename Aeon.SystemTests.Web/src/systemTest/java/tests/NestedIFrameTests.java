package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.iframe.IFrameWikiSample;
import main.iframe.NestedIFrameWikiSample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working on a UltiPro environment and to test the switch mechanism.
 */
public class NestedIFrameTests {
    private static NestedIFrameWikiSample nestedWikiPage;

    @Before
    public void beforeIFrameTests() {
        nestedWikiPage = launch(NestedIFrameWikiSample.class);
        String nestedEnvironment = nestedWikiPage.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/nestedIFrame.html");
        String nestedProtocol = nestedWikiPage.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        nestedWikiPage.browser.goToUrl(nestedProtocol + "://" + nestedEnvironment);
    }

    @After
    public void afterIFrameTests() {
        nestedWikiPage.browser.quit();
    }

    @Test
    public void nestedIFrameSwitchTest(){
        nestedWikiPage.nestedIFramePage.wikiSearchTextBox.click();
        nestedWikiPage.nestedIFramePage.searchButton.click();
        nestedWikiPage.nestedIFramePage.wikiSearchTextBox.click();
        nestedWikiPage.nestedIFramePage.wikiSearchTextBox.set("Ultimate Software");
        nestedWikiPage.nestedIFramePage.searchButton.click();
        nestedWikiPage.nestedIFramePage.wikiLogo.click();
        nestedWikiPage.nestedIFramePage.popupButton.click();
        nestedWikiPage.browser.switchToWindowByTitle("Bing");
        nestedWikiPage.browser.verifyTitle("Bing");
        nestedWikiPage.browser.switchToWindowByTitle("NestedTitle");
        nestedWikiPage.browser.verifyTitle("NestedTitle");
    }
}

