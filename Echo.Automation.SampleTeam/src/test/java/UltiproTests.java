import echo.core.common.web.BrowserType;
import main.ultipro.Ultipro;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static echo.core.test_abstraction.product.Echo.Launch;

/**
 * Created by SebastianR on 11/23/2016.
 * This test file was created to show Next working on a Ultipro environment and to test the switch mechansim.
 * These tests are currently being ignored because the environment used is not persistent.
 */

public class UltiproTests {
    private static Ultipro ultipro;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void BeforeTests() {
        ultipro = Launch(Ultipro.class, BrowserType.Chrome);
        ultipro.browser.Maximize();
        ultipro.browser.GoToUrl("http://apertureweb/Login.aspx");
    }

    @After
    public void AfterTests() {
        ultipro.browser.Quit();
    }

    @Test
    public void NewHireTest(){
        ultipro.loginPage.UserNameTextBox.Set("wardenj");
        ultipro.loginPage.PasswordTextBox.Set("password");
        ultipro.loginPage.LoginButton.Click();
        ultipro.homePage.menuButton.Click();
        ultipro.homePage.myTeam.Click();
        ultipro.homePage.myEmployees.Click();
        ultipro.myEmployeesPage.addEmployeeButton.Click();
        ultipro.browser.SwitchToWindowByTitle("Hire an Employee");
        ultipro.browser.Maximize();
        ultipro.newHireWizard.startPage.SSNTextBox.Set("123987654");
        ultipro.newHireWizard.startPage.confirmSSNTextBox.Set("123987654");
        ultipro.newHireWizard.startPage.firstName.Set("FirstName");
        ultipro.newHireWizard.startPage.lastName.Set("LastName");
        ultipro.newHireWizard.startPage.employeeNumber.Set("12345");
        ultipro.newHireWizard.nextButton.Click();
        ultipro.newHireWizard.personalPage.addressLine.Set("605 Sw 10th St");
        ultipro.newHireWizard.backButton.Click();
        ultipro.newHireWizard.cancelButton.Click();
        ultipro.browser.AcceptAlert();
        ultipro.browser.SwitchToWindowByTitle("John H Warden II - 823567403 - Ultimate Smoke Company");
        ultipro.homePage.menuButton.Click();
        ultipro.homePage.homeButton.Click();
    }

    @Test
    public void GridWithSwitchTest(){
        ultipro.loginPage.UserNameTextBox.Set("wardenj");
        ultipro.loginPage.PasswordTextBox.Set("password");
        ultipro.loginPage.LoginButton.Click();
        ultipro.homePage.menuButton.Click();
        ultipro.homePage.myTeam.Click();
        ultipro.homePage.myEmployees.Click();
        ultipro.myEmployeesPage.searchButton.Click();
        ultipro.myEmployeesPage.myGrid.RowBy.employeeNumber("823567416").getRow().employeeLink.Click();
        ultipro.browser.SwitchToWindowByTitle("Wayan Bing");
        ultipro.browser.VerifyTitle("Wayan Bing - 823567416 - USA Smoke Company 1");
    }
}

