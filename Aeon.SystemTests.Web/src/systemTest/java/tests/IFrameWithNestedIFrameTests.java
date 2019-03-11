package tests;

import aeon.core.testabstraction.product.WebConfiguration;
import main.iframewithnestediframe.IFrameWithNestedIFrameWikiSample;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * This test file was created to show Aeon working with iFrames within iFrames and to test the switch mechanism.
 */
public class IFrameWithNestedIFrameTests {
    private static IFrameWithNestedIFrameWikiSample iFrameWithinIFrame;

    @Before
    public void beforeIFrameTests() {
        iFrameWithinIFrame = launch(IFrameWithNestedIFrameWikiSample.class);
        String nestedEnvironment = iFrameWithinIFrame.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/IFrameWithNestedIFrame.html");
        String nestedProtocol = iFrameWithinIFrame.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        iFrameWithinIFrame.browser.goToUrl(nestedProtocol + "://" + nestedEnvironment);
    }

    @After
    public void afterIFrameTests() {
        iFrameWithinIFrame.browser.quit();
    }

    @Test
    public void nestedIFrameSwitchTest(){
        iFrameWithinIFrame.iFrameWithNestedIFrame.wikiSearchTextBox.click();
        iFrameWithinIFrame.iFrameWithNestedIFrame.searchButton.click();
        iFrameWithinIFrame.iFrameWithNestedIFrame.wikiSearchTextBox.click();
        iFrameWithinIFrame.iFrameWithNestedIFrame.wikiSearchTextBox.set("Ultimate Software");
        iFrameWithinIFrame.iFrameWithNestedIFrame.searchButton.click();
        iFrameWithinIFrame.iFrameWithNestedIFrame.wikiLogo.click();
        iFrameWithinIFrame.iFrameWithNestedIFrame.popupButton.click();
        iFrameWithinIFrame.browser.switchToWindowByTitle("Bing");
        iFrameWithinIFrame.browser.verifyTitle("Bing");
        iFrameWithinIFrame.browser.switchToWindowByTitle("NestedTitle");
        iFrameWithinIFrame.browser.verifyTitle("NestedTitle");
    }
}

