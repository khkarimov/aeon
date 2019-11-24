package tests;

import org.testng.annotations.Test;

/**
 * This test file was created to show Aeon working with iFrames on webpages to test the switch mechanism.
 */
public class DemoTest extends BaseSetup {

    @Test
    public void demoTest() {
        product.homePage.searchBox.exists();
        product.homePage.searchBox.set("Software Testing");
        product.homePage.searchBtn.click();
        product.homePage.wikiOption.exists();
        product.homePage.wikiOption.click();

        product.browser.verifyTitle("Software testing - Wikipedia");
    }
}

