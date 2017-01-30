/**
 * Created by josepe on 1/27/2017.
 */

import echo.core.common.web.BrowserType;
import main.Sample;
import org.junit.*;
import org.junit.rules.ExpectedException;
import static echo.core.test_abstraction.product.Echo.Launch;

public class WindowsFirefoxBrowserTests {
    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void SetUp() {
    }

    @AfterClass
    public static void TearDown() {
    }

    @Before
    public void BeforeTests() {
        product = Launch(Sample.class, BrowserType.Firefox);
        product.browser.Maximize();
        product.browser.GoToUrl("file:///" + System.getProperty("user.dir").replace('\\', '/') + "/Test%20Sample%20Context/index.html");
    }

    @After
    public void AfterTests() {
        product.browser.Quit();
    }
    //endregion

    @Test
    public void TestSelectFileDialog_OpenFileDialog() {
        String path = System.getProperty("user.dir") + "\\Test Sample Context\\HeatLogo.jpg";
        product.StartPage.TestFileDialogInput.OpenFileDialog();
        product.StartPage.TestFileDialogInput.SelectFileDialog(path);
    }
}
