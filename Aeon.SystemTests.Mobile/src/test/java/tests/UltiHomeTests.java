package tests;

import categories.BrowserTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import ultihome.UltiHome;

import static aeon.core.testabstraction.product.Aeon.launch;

@Category({BrowserTests.class})
public class UltiHomeTests {

    private UltiHome ultiHome;

    @Before
    public void setUp() {
        ultiHome = launch(UltiHome.class);
        ultiHome.mobileDevice.goToUrl("https://ultihome.ultimatesoftware.com");
        //ultiHome.browser.goToUrl("https://ultihome.ultimatesoftware.com");
    }

    @After
    public void tearDown() {
        ultiHome.mobileDevice.quit();
    }

    @Test
    public void ultihomeTest() {
        ultiHome.homePage.userField.set("test username");
        ultiHome.homePage.passField.set("test password");
        ultiHome.homePage.loginButton.click();
    }
}
