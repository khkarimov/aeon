import echo.core.common.helpers.Sleep;
import echo.core.common.web.BrowserType;
import main.Sample;
import main.ultipro.Ultipro;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static echo.core.test_abstraction.product.Echo.Launch;

/**
 * Created by SebastianR on 11/23/2016.
 */
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
        ultipro.browser.GoToUrl("http://sr1web/Login.aspx");
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
        Sleep.Wait(2000);
        ultipro.myEmployeesPage.addEmployeeButton.Click();
        ultipro.browser.SwitchToWindowByTitle("Hire an Employee");
        ultipro.browser.Maximize();
        ultipro.newHirePage.SSNTextBox.Set("123987654");
        ultipro.newHirePage.confirmSSNTextBox.Set("123987654");
        ultipro.newHirePage.cancelButton.Click();
        ultipro.browser.AcceptAlert();
    }

    @Test
    public void SearchEmployeeTest(){
        ultipro.loginPage.userNameTextBox.Set("wardenj");
        ultipro.loginPage.passwordTextBox.Set("password");
        ultipro.loginPage.loginButton.Click();
        ultipro.homePage.menuButon.Click();
        ultipro.homePage.myTeam.Click();
        ultipro.homePage.myEmployees.Click();
        Sleep.Wait(2000);
        ultipro.myEmployeesPage.employeeSearchBox.Set("John");
    }
}

