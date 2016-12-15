import echo.core.common.helpers.Sleep;
import echo.core.common.web.BrowserType;
import main.ultipro.Ultipro;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static echo.core.test_abstraction.product.Echo.Launch;

/**
 * Created by SebastianR on 11/23/2016.
 * This test is currently being ignored because the environments used is not persistent.
 */

@Ignore
public class UltiproTests {
    private static Ultipro ultipro;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //region Setup and Teardown
    @BeforeClass
    public static void SetUp() {
    }

    @AfterClass
    public static void TearDown() {
        //product.browser.Quit();
    }

    @Before
    public void BeforeTests() {
        ultipro = Launch(Ultipro.class, BrowserType.Chrome);
        ultipro.browser.Maximize();
        ultipro.browser.GoToUrl("http://sr2web/Login.aspx");
    }

    @After
    public void AfterTests() {
        ultipro.browser.Quit();
    }
//endregion

    @Test
    public void NewHireTest(){
        ultipro.loginPage.userNameTextBox.Set("wardenj");
        ultipro.loginPage.passwordTextBox.Set("password");
        ultipro.loginPage.loginButton.Click();
        ultipro.homePage.menuButon.Click();
        ultipro.homePage.myTeam.Click();
        ultipro.homePage.myEmployees.Click();
        Sleep.Wait(1000);
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
        Sleep.Wait(500);
        ultipro.homePage.menuButon.Click();
        ultipro.homePage.homeButton.Click();
    }
}

