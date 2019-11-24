package tests;

import main.sample.Product;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import static com.ultimatesoftware.aeon.core.testabstraction.product.Aeon.launch;

public class BaseSetup {

    public static Product product;

    @BeforeClass
    public static void setup() {
        product = launch(Product.class);
    }

    @AfterClass
    public static void tearDown() {
        product.browser.quit();
    }
}
