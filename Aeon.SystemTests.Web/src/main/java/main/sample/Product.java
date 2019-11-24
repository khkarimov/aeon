package main.sample;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.sample.pages.HomePage;

/**
 * Product web product.
 */
public class Product extends WebProduct {
    public final HomePage homePage;

    /**
     * Create a new instance of the Product models.
     *
     * @param automationInfo The automation info object to use.
     */
    public Product(AutomationInfo automationInfo) {
        super(automationInfo);

        homePage = new HomePage(getAutomationInfo());
    }
}
