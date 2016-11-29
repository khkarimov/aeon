package main.ultipro;

import echo.core.test_abstraction.product.WebProduct;

/**
 * Created by SebastianR on 11/23/2016.
 */
public class Ultipro extends WebProduct{
    public LoginPage loginPage;
    public HomePage homePage;
    public MyEmployeesPage myEmployeesPage;
    public NewHirePage newHirePage;

    public Ultipro(){
    }

    @Override
    protected void afterLaunch() {
        super.afterLaunch();
        loginPage = new LoginPage(getAutomationInfo());
        homePage = new HomePage(getAutomationInfo());
        myEmployeesPage = new MyEmployeesPage(getAutomationInfo());
        newHirePage = new NewHirePage(getAutomationInfo());
    }
}
