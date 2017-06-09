import aeon.core.common.web.BrowserType;
import aeon.core.testabstraction.product.Configuration;
import main.Sample;
import org.junit.*;
import org.junit.rules.ExpectedException;
import static aeon.core.testabstraction.product.Aeon.launch;

public class WindowsFirefoxBrowserTests {
    private static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void setUp() {
    }

    @AfterClass
    public static void tearDown() {
    }

    @Before
    public void beforeTests() {
        product = launch(Sample.class, BrowserType.Firefox);
        String environment = product.getConfig(Configuration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(Configuration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }
    //endregion

    @Test
    public void testSelectFileDialog_OpenFileDialog() {
        String path = System.getProperty("user.dir") + "\\Test-Sample-Context\\HeatLogo.jpg";
        product.startPage.testFileDialogInput.openFileDialog();
        product.startPage.testFileDialogInput.selectFileDialog(path);
    }
}
