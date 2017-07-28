package tests;

import aeon.core.common.web.BrowserType;
import main.ultipro.Ultipro;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * Created by SebastianR on 11/23/2016.
 * This test file was created to show Next working on a Ultipro environment and to test the switch mechanism.
 * These tests are currently being ignored because the environment used is not persistent.
 */

public class UltiproTests {
    private static Ultipro ultipro;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeTests() {
        ultipro = launch(Ultipro.class, BrowserType.Chrome);
        ultipro.browser.maximize();
        ultipro.browser.goToUrl("http://legendsmkweb.mia.ucloud.int");
    }

    @After
    public void afterTests() {
        ultipro.browser.quit();
    }

    @Test
    public void newHireTest(){
        ultipro.loginPage.userNameTextBox.set("wardenj");
        ultipro.loginPage.passwordTextBox.set("password");
        ultipro.loginPage.loginButton.click();
        ultipro.homePage.menuButton.click();
        ultipro.homePage.myTeam.click();
        ultipro.homePage.myEmployees.click();
        ultipro.myEmployeesPage.addEmployeeButton.click();
        ultipro.browser.switchToWindowByTitle("Hire an Employee");
        ultipro.browser.maximize();
        ultipro.newHireWizard.startPage.ssnTextBox.set("123987654");
        ultipro.newHireWizard.startPage.confirmSSNTextBox.set("123987654");
        ultipro.newHireWizard.startPage.firstName.set("FirstName");
        ultipro.newHireWizard.startPage.lastName.set("LastName");
        ultipro.newHireWizard.startPage.employeeNumber.set("12345");
        ultipro.newHireWizard.nextButton.click();
        ultipro.newHireWizard.personalPage.addressLine.set("605 Sw 10th St");
        ultipro.newHireWizard.backButton.click();
        ultipro.newHireWizard.cancelButton.click();
        ultipro.browser.acceptAlert();
        ultipro.browser.switchToWindowByTitle("John H Warden II - 823567403 - Ultimate Smoke Company");
        ultipro.homePage.menuButton.click();
        ultipro.homePage.homeButton.click();
    }

    @Test
    public void gridWithSwitchTest(){
        ultipro.loginPage.userNameTextBox.set("wardenj");
        ultipro.loginPage.passwordTextBox.set("password");
        ultipro.loginPage.loginButton.click();
        ultipro.homePage.menuButton.click();
        ultipro.homePage.myTeam.click();
        ultipro.homePage.myEmployees.click();
        ultipro.myEmployeesPage.searchButton.click();
        ultipro.myEmployeesPage.myGrid.rowBy.employeeNumber("823567416").getRow().employeeLink.click();
        ultipro.browser.switchToWindowByTitle("Wayan Bing");
        ultipro.browser.verifyTitle("Wayan Bing - 823567416 - USA Smoke Company 1");
    }
}

