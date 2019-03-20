package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.pagewithiframeandnestediframe.PageWithIFrameAndNestedIFrameWikiSample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working with iFrames within iFrames and to test the switch mechanism.
 */
public class PageWithIFrameAndNestedIFrameTests {
    private static PageWithIFrameAndNestedIFrameWikiSample pageWithIFrameAndNestedIFrameWikiSample;

    @Before
    public void beforeIFrameTests() {
        pageWithIFrameAndNestedIFrameWikiSample = launch(PageWithIFrameAndNestedIFrameWikiSample.class);
        String nestedEnvironment = pageWithIFrameAndNestedIFrameWikiSample.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/PageWithIFrameAndNestedIFrame.html");
        String nestedProtocol = pageWithIFrameAndNestedIFrameWikiSample.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        pageWithIFrameAndNestedIFrameWikiSample.browser.goToUrl(nestedProtocol + "://" + nestedEnvironment);
    }

    @After
    public void afterIFrameTests() {
        pageWithIFrameAndNestedIFrameWikiSample.browser.quit();
    }

    @Test
    public void nestedIFrameSwitchTest(){
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.wikiSearchTextBox.click();
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.searchButton.click();
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.wikiSearchTextBox.click();
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.wikiSearchTextBox.set("Ultimate Software");
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.searchButton.click();
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.iFrame.wikiLogo.click();
        pageWithIFrameAndNestedIFrameWikiSample.pageWithIFrameAndNestedIFrame.popupButton.click();
        pageWithIFrameAndNestedIFrameWikiSample.browser.switchToWindowByTitle("Bing");
        pageWithIFrameAndNestedIFrameWikiSample.browser.verifyTitle("Bing");
        pageWithIFrameAndNestedIFrameWikiSample.browser.switchToWindowByTitle("NestedTitle");
        pageWithIFrameAndNestedIFrameWikiSample.browser.verifyTitle("NestedTitle");
    }
}

