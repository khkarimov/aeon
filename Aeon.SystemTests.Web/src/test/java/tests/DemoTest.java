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

        product.homePage.text.exists();
        product.homePage.text.isLike("As the number of possible tests for even simple " +
                "software components is practically infinite, all software testing uses some" +
                " strategy to select tests that are feasible for the available time and resources.");
    }
}

