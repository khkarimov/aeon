package main.ultipro;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.by;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.TextBox;
import main.ultipro.my_employees_grid.MyEmployeesGrid;
import main.ultipro.my_employees_grid.MyEmployeesHeaders;

import java.util.ArrayList;


/**
 * Created by SebastianR on 11/23/2016.
 */
public class MyEmployeesPage {
    public Button addEmployeeButton;
    public TextBox employeeSearchBox;
    public MyEmployeesGrid myGrid;
    public Button searchButton;

    public MyEmployeesPage(AutomationInfo automationInfo) {
        addEmployeeButton = new Button(automationInfo, by.CssSelector("#ctl00_btnAdd"), getSwitchForContentFrame());
        employeeSearchBox = new TextBox(automationInfo, by.CssSelector("#GridView1_TextEntryFilterControlInputBox_0"), getSwitchForContentFrame());
        myGrid = new MyEmployeesGrid(new MyEmployeesHeaders(automationInfo, by.CssSelector("#ctl00_Content_GridView1"), getSwitchForContentFrame()));
        searchButton = new Button(automationInfo, by.CssSelector("#GridView1_filterButton"), getSwitchForContentFrame());
    }

    //Since all the elements on this page are in the content frame
    private ArrayList<IBy> getSwitchForContentFrame(){
        ArrayList<IBy> switchMechanism = new ArrayList<>();
        switchMechanism.add(by.CssSelector("iframe[id*=ContentFrame]"));
        return switchMechanism;
    }
}
