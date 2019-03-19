package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;
import main.ultipro.myemployeesgrid.EmployeeTable;
import main.ultipro.myemployeesgrid.EmployeeTableContainer;


/**
 * The page for managing employees.
 */
public class MyEmployeesPage implements Page {

    public Button addEmployeeButton;
    public TextBox employeeSearchBox;
    public EmployeeTableContainer myGrid;
    public Button searchButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public MyEmployeesPage(AutomationInfo automationInfo) {
        addEmployeeButton = new Button(automationInfo, By.cssSelector("#ctl00_btnAdd"), By.cssSelector("iframe[id*=ContentFrame]"));
        employeeSearchBox = new TextBox(automationInfo, By.cssSelector("#GridView1_TextEntryFilterControlInputBox_0"), By.cssSelector("iframe[id*=ContentFrame]"));
        myGrid = new EmployeeTableContainer(automationInfo, By.cssSelector("#ctl00_Content_GridView1"), new EmployeeTable(), By.cssSelector("iframe[id*=ContentFrame]"));
        searchButton = new Button(automationInfo, By.cssSelector("#GridView1_filterButton"), By.cssSelector("iframe[id*=ContentFrame]"));
    }
}
