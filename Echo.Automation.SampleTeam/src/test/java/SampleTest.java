import echo.core.test_abstraction.context.Echo;
import main.Sample;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static echo.core.common.BrowserType.Chrome;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SampleTest {

    private static Sample product;

    @BeforeClass
    public static void fixtureSetUp() {
        product = Echo.Launch(Sample.class, Chrome);
        //product.Browser.NavigateTo("http://devausql03/ABsCarSearch");
    }

    @AfterClass
    public static void fixtureTearDown() {
        //product.Browser.Quit();
    }

    @Test
    public void SampleTest() {
        //product.Main.Submit.Click();

        // Grid Examples
        //product.Main.MyGrid.Row.ById("12345").ByName("bob").NameLabel.Exists();
        //product.Main.MyGrid.Row.ById("12345").ByName("bob").descriptionTextBox.Set("text to set");
        //product.Main.MyGrid.Row.ById("12345").ByName("bob").IdLink.Click();
    }
}
