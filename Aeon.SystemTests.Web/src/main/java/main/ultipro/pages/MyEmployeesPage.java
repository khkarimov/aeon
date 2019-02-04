package main.ultipro.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;
import main.ultipro.myemployeesgrid.EmployeeTableContainer;
import main.ultipro.myemployeesgrid.MyEmployeesGrid;

import java.util.ArrayList;


/**
 * The page for managing employees.
 */
public class MyEmployeesPage extends Page {

    public Button addEmployeeButton;
    public TextBox employeeSearchBox;
    public MyEmployeesGrid myGrid;
    public Button searchButton;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public MyEmployeesPage(AutomationInfo automationInfo) {
        addEmployeeButton = new Button(automationInfo, By.cssSelector("#ctl00_btnAdd"), getSwitchForContentFrame());
        employeeSearchBox = new TextBox(automationInfo, By.cssSelector("#GridView1_TextEntryFilterControlInputBox_0"), getSwitchForContentFrame());
        myGrid = new MyEmployeesGrid(automationInfo, By.cssSelector("#ctl00_Content_GridView1"), new EmployeeTableContainer(), getSwitchForContentFrame());
        searchButton = new Button(automationInfo, By.cssSelector("#GridView1_filterButton"), getSwitchForContentFrame());
    }

    //Since all the elements on this page are in the content frame
    private IByWeb[] getSwitchForContentFrame() {
        ArrayList<IByWeb> switchMechanism = new ArrayList<>();
        switchMechanism.add(By.cssSelector("iframe[id*=ContentFrame]"));
        return (IByWeb[]) switchMechanism.toArray();
    }
}
