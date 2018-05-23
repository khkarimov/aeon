package tests;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.WebConfiguration;
import main.sample.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;


import static aeon.core.testabstraction.product.Aeon.launch;

@Tag("WindowsTests")
@Tag("UbuntuTests")
public class SampleBaseTest {

    public static Sample product;
    private static List<Map<String, Map<String, List<String>>>> watchLog = new ArrayList<>();

    /*
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
    */
    @BeforeEach
    public void beforeTests() {
        product = launch(Sample.class);
        String environment = product.getConfig(WebConfiguration.Keys.ENVIRONMENT,
                "/" + System.getProperty("user.dir").replace('\\', '/') + "/Test-Sample-Context/index.html");
        String protocol = product.getConfig(WebConfiguration.Keys.PROTOCOL, "file");
        product.browser.goToUrl(protocol + "://" + environment);
    }

    @AfterEach
    public void afterTests() {
        product.browser.quit();
    }

    @AfterClass
    public static void afterClass() {
        Aeon.done();
    }
}
