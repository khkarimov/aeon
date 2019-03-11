package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.pagewithiframe.IFrameWikiSample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working with iFrames on webpages to test the switch mechanism.
 */
public class IFrameTests {
    private static IFrameWikiSample wikiPage;

    @Before
    public void beforeIFrameTests() {
        wikiPage = launch(IFrameWikiSample.class);
        String environment = wikiPage.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/PageWithIFrame.html");
        String protocol = wikiPage.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        wikiPage.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterIFrameTests() {
        wikiPage.browser.quit();
    }

    @Test
    public void iFrameWithSwitchTest(){
        wikiPage.pageWithIFrame.wikiSearchTextBox.click();
        wikiPage.pageWithIFrame.searchButton.click();
        wikiPage.pageWithIFrame.wikiSearchTextBox.click();
        wikiPage.pageWithIFrame.wikiSearchTextBox.set("Ultimate Software");
        wikiPage.pageWithIFrame.searchButton.click();
        wikiPage.pageWithIFrame.wikiLogo.click();
        wikiPage.pageWithIFrame.popupButton.click();
        wikiPage.browser.switchToWindowByTitle("Bing");
        wikiPage.browser.verifyTitle("Bing");
        wikiPage.browser.switchToWindowByTitle("Title");
        wikiPage.browser.verifyTitle("Title");
    }
}

