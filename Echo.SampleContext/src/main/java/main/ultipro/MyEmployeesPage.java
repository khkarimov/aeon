package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.TextBox;

import java.util.ArrayList;


/**
 * Created by SebastianR on 11/23/2016.
 */
public class MyEmployeesPage {
    private AutomationInfo automationInfo;

    public Button addEmployeeButton;
    public TextBox employeeSearchBox;

    public MyEmployeesPage(AutomationInfo automationInfo) {
        this.automationInfo = automationInfo;
        addEmployeeButton = new Button(automationInfo, By.CssSelector("#ctl00_btnAdd"), getSwitchForContentFrame());
        employeeSearchBox = new TextBox(automationInfo, By.CssSelector("#GridView1_TextEntryFilterControlInputBox_0"), getSwitchForContentFrame());
    }

    //Since all the elements on this page are in the content frame
    private ArrayList<IBy> getSwitchForContentFrame(){
        ArrayList<IBy> switchMechanism = new ArrayList<>();
        switchMechanism.add(By.CssSelector("iframe[id*=ContentFrame]"));
        return switchMechanism;
    }
}
