package main.ultipro;

import aeon.core.testabstraction.product.WebProduct;
import main.ultipro.pages.HomePage;
import main.ultipro.pages.LoginPage;
import main.ultipro.pages.MyEmployeesPage;
import main.ultipro.pages.NewHireWizard;

/**
 * Sample UltiPro product.
 */
public class UltiPro extends WebProduct{
    public LoginPage loginPage;
    public HomePage homePage;
    public MyEmployeesPage myEmployeesPage;
    public NewHireWizard newHireWizard;

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        loginPage = new LoginPage(getAutomationInfo());
        homePage = new HomePage(getAutomationInfo());
        myEmployeesPage = new MyEmployeesPage(getAutomationInfo());
        newHireWizard = new NewHireWizard(getAutomationInfo());
    }
}
