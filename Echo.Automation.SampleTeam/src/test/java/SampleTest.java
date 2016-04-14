import main.Sample;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static echo.core.common.BrowserType.Firefox;
import static echo.core.test_abstraction.context.Echo.Launch;

/**
 * Created by DionnyS on 4/13/2016.
 */
public class SampleTest {

    private static Sample product;

    @BeforeClass
    public static void fixtureSetUp() {
        product = Launch(Sample.class, Firefox);
        //product.Browser.NavigateTo("http://devausql03/ABsCarSearch");
    }

    @AfterClass
    public static void fixtureTearDown() {
        //product.Browser.Quit();
    }

    @Test
    public void SampleTest() {
        //product.Main.Submit.Click();

        //product.Main.SomeSikuliControl.Click();

//        product.SwitchDriver(Sikulu.class);
//        product.Main.SikuliControl.SikuliCommand();
//        product.SwitchDriver(Selenium.class);

        /*
         row = product.Main.MyGrid.SelectRow(ById("").ByName(""))
         product.Main.MyGrid.RowById("12345").AndName("bob").NameLabel.Exists();

          */

        // Grid Examples
//        product.Main.MyGrid.Row.ById("12345").ByName("bob").NameLabel.Exists();
//        product.Main.MyGrid.Row.ById("12345").ByName("bob").descriptionTextBox.Set("text to set");
//        product.Main.MyGrid.Row.ById("12345").ByName("bob").IdLink.Click();
    }
}
