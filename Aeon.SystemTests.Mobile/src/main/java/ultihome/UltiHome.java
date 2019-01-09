package ultihome;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.testabstraction.product.MobileProduct;
import ultihome.pages.HomePage;

/**
 * Sample UltiHome web product.
 */
public class UltiHome extends MobileProduct {

    public HomePage homePage;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        AutomationInfo info = getAutomationInfo();

        homePage = new HomePage(info);
    }
}
