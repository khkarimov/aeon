package main.ultipro;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.testabstraction.product.WebProduct;
import main.ultipro.pages.HomePage;
import main.ultipro.pages.LoginPage;
import main.ultipro.pages.MyEmployeesPage;
import main.ultipro.pages.NewHireWizard;

/**
 * Sample UltiPro product.
 */
public class UltiPro extends WebProduct {
    public LoginPage loginPage;
    public HomePage homePage;
    public MyEmployeesPage myEmployeesPage;
    public NewHireWizard newHireWizard;

    /**
     * Create new browser using a provided AutomationInfo variable.
     *
     * @param automationInfo An AutomationInfo object provided to the function.
     */
    protected UltiPro(AutomationInfo automationInfo) {
        super(automationInfo);
        loginPage = new LoginPage(getAutomationInfo());
        homePage = new HomePage(getAutomationInfo());
        myEmployeesPage = new MyEmployeesPage(getAutomationInfo());
        newHireWizard = new NewHireWizard(getAutomationInfo());
    }
}
