package tests;

import aeon.core.common.web.BrowserType;
import main.ultipro.UltiPro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static aeon.core.testabstraction.product.Aeon.launch;

/**
 * Created by SebastianR on 11/23/2016.
 * This test file was created to show Aeon working on a UltiPro environment and to test the switch mechanism.
 */
public class UltiProTests {
    private static UltiPro ultiPro;

    @BeforeEach
    public void beforeTests() {
        ultiPro = launch(UltiPro.class, BrowserType.Chrome);
        ultiPro.browser.maximize();
        ultiPro.browser.goToUrl("http://legendsmkweb.mia.ucloud.int");
    }

    @AfterEach
    public void afterTests() {
        ultiPro.browser.quit();
    }

    @Test
    public void newHireTest(){
        ultiPro.loginPage.userNameTextBox.set("wardenj");
        ultiPro.loginPage.passwordTextBox.set("password");
        ultiPro.loginPage.loginButton.click();
        ultiPro.homePage.menuButton.click();
        ultiPro.homePage.myTeam.click();
        ultiPro.homePage.myEmployees.click();
        ultiPro.myEmployeesPage.addEmployeeButton.click();
        ultiPro.browser.switchToWindowByTitle("Hire an Employee");
        ultiPro.browser.maximize();
        ultiPro.newHireWizard.startPage.ssnTextBox.set("123987654");
        ultiPro.newHireWizard.startPage.confirmSSNTextBox.set("123987654");
        ultiPro.newHireWizard.startPage.firstName.set("FirstName");
        ultiPro.newHireWizard.startPage.lastName.set("LastName");
        ultiPro.newHireWizard.startPage.employeeNumber.set("12345");
        ultiPro.newHireWizard.nextButton.click();
        ultiPro.newHireWizard.personalPage.addressLine.set("605 Sw 10th St");
        ultiPro.newHireWizard.backButton.click();
        ultiPro.newHireWizard.cancelButton.click();
        ultiPro.browser.acceptAlert();
        ultiPro.browser.switchToWindowByTitle("John H Warden II - 823567403 - Ultimate Smoke Company");
        ultiPro.homePage.menuButton.click();
        ultiPro.homePage.homeButton.click();
    }

    @Test
    public void gridWithSwitchTest(){
        ultiPro.loginPage.userNameTextBox.set("wardenj");
        ultiPro.loginPage.passwordTextBox.set("password");
        ultiPro.loginPage.loginButton.click();
        ultiPro.homePage.menuButton.click();
        ultiPro.homePage.myTeam.click();
        ultiPro.homePage.myEmployees.click();
        ultiPro.myEmployeesPage.searchButton.click();
        ultiPro.myEmployeesPage.myGrid.rowBy.employeeNumber("823567416").getRow().employeeLink.click();
        ultiPro.browser.switchToWindowByTitle("Wayan Bing");
        ultiPro.browser.verifyTitle("Wayan Bing - 823567416 - USA Smoke Company 1");
    }
}

