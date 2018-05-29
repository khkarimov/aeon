package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Tag;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static aeon.core.testabstraction.product.Aeon.launch;
import static aeon.core.testabstraction.product.AeonTestExecution.startTest;

@Tag("WindowsTests")
@Tag("UbuntuTests")
public class SampleBaseTest {

    public static Sample product;

    private static String watchLog = "";

    @Rule
    public TestRule watchMan = new TestWatcher() {

        @Override
        protected void starting(Description description) {
            startTest(description.getMethodName() + "." + description.getClassName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            watchLog += "Failed Test " + description.getMethodName() + " in class " + description.getClassName() + "\n";
        }

        @Override
        protected void succeeded(Description description) {
            watchLog += "Succeeded Test " + description.getMethodName() + " in class " + description.getClassName() + "\n";
        }
    };


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

    @AfterClass
    public static void afterClass() {
        Aeon.done();
    }
}
