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
public class PageWithIFrameTests {
    private static IFrameWikiSample iFrameWikiSample;

    @Before
    public void beforeIFrameTests() {
        iFrameWikiSample = launch(IFrameWikiSample.class);
        String environment = iFrameWikiSample.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/PageWithIFrame.html");
        String protocol = iFrameWikiSample.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        iFrameWikiSample.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterIFrameTests() {
        iFrameWikiSample.browser.quit();
    }

    @Test
    public void iFrameWithSwitchTest(){
        iFrameWikiSample.pageWithIFrame.wikiSearchTextBox.click();
        iFrameWikiSample.pageWithIFrame.searchButton.click();
        iFrameWikiSample.pageWithIFrame.wikiSearchTextBox.click();
        iFrameWikiSample.pageWithIFrame.wikiSearchTextBox.set("Ultimate Software");
        iFrameWikiSample.pageWithIFrame.searchButton.click();
        iFrameWikiSample.pageWithIFrame.wikiLogo.click();
        iFrameWikiSample.pageWithIFrame.popupButton.click();
        iFrameWikiSample.browser.switchToWindowByTitle("Bing");
        iFrameWikiSample.browser.verifyTitle("Bing");
        iFrameWikiSample.browser.switchToWindowByTitle("Title");
        iFrameWikiSample.browser.verifyTitle("Title");
    }
}

