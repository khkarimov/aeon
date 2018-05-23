package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import categories.UbuntuTests;
import categories.WindowsTests;
import main.sample.Sample;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static aeon.core.testabstraction.product.Aeon.launch;

@Category({WindowsTests.class, UbuntuTests.class})
public class SampleBaseTest {

    public static Sample product;
    private static List<Map<String, Map<String, List<String>>>> watchLog = new ArrayList<>();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TestWatcher watchMan = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            for (Map<String, Map<String, List<String>>> map : watchLog) {
                if (map.getOrDefault(description.getClassName(), null) == null) {
                    Map<String, List<String>> hash = new HashMap<>();
                    hash.put("failed", new ArrayList<>());
                    hash.get("failed").add(description.getMethodName());
                    Map<String, Map<String, List<String>>> outerHash = new HashMap<>();
                    outerHash.put(description.getClassName(), hash);
                    watchLog.add(outerHash);
                } else {
                    map.get(description.getClassName()).get("failed").add(description.getMethodName());
                }
            }
        }

        @Override
        protected void succeeded(Description description) {
            for (Map<String, Map<String, List<String>>> map : watchLog) {
                if (map.getOrDefault(description.getClassName(), null) == null) {
                    Map<String, List<String>> hash = new HashMap<>();
                    hash.put("succeeded", new ArrayList<>());
                    hash.get("succeeded").add(description.getMethodName());
                    Map<String, Map<String, List<String>>> outerHash = new HashMap<>();
                    outerHash.put(description.getClassName(), hash);
                    watchLog.add(outerHash);
                } else {
                    map.get(description.getClassName()).get("succeeded").add(description.getMethodName());
                }
            }
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
