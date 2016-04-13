import echo.core.test_abstraction.context.Echo;
import main.Sample;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static echo.core.common.BrowserType.Chrome;
import static echo.core.common.BrowserType.Firefox;
import static echo.core.common.BrowserType.InternetExplorer;
import static echo.core.test_abstraction.context.Echo.*;

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
//        product.Browser.Quit();
    }

    @Test
    public void SampleTest() {
//        product.Main.Submit.Click();

//        product.EeMain
//                .EmployeeGrid
//                .Row.byId("someId").byName("name")
//                .Element
//                .Click();
//
//        product.EeMain
//                .EmployeeGrid
//                .Row.byId("someId").andName("name")
//                .Element
//                .Click();
//
//        product.EeMain
//                .EmployeeGrid
//                .RowById("someId").andName("name")
//                .Element
//                .Click();

    }
}
