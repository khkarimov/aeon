package main.sample;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.sample.pages.SampleUltiHomePage;
import main.sample.pages.VTeamSamplePage;

/**
 * Sample web product.
 */
public class Sample extends WebProduct {
    public final VTeamSamplePage startPage;
    public final SampleUltiHomePage ultihome;

    /**
     * Create a new instance of the Sample models.
     *
     * @param automationInfo The automation info object to use.
     */
    public Sample(AutomationInfo automationInfo) {
        super(automationInfo);

        startPage = new VTeamSamplePage(getAutomationInfo());
        ultihome = new SampleUltiHomePage(getAutomationInfo());
    }
}
