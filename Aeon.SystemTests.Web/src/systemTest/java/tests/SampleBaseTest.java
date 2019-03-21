package tests;

import categories.UbuntuTests;
import categories.WindowsTests;
import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static com.ultimatesoftware.aeon.core.testabstraction.product.Aeon.launch;
import static com.ultimatesoftware.aeon.core.testabstraction.product.AeonTestExecution.*;

@Category({WindowsTests.class, UbuntuTests.class})
public class SampleBaseTest {

    public static Sample product;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestRule watchMan = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            String className = description.getClassName().substring(description.getClassName().lastIndexOf(".") + 1, description.getClassName().length());
            startTest(className + "." + description.getMethodName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            testFailed(e.getMessage(), e);
        }

        @Override
        protected void succeeded(Description description) {
            testSucceeded();
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            String className = description.getClassName().substring(description.getClassName().lastIndexOf(".") + 1, description.getClassName().length());
            testSkipped(className + "." + description.getMethodName());
        }
    };

    @BeforeClass
    public static void oneTimeSetup() {
        beforeStart();
    }

    @AfterClass
    public static void afterClass() {
        Aeon.done();
    }

    @Before
    public void beforeTests() {
        product = launch(Sample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @After
    public void afterTests() {
        product.browser.quit();
    }
}
