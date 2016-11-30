package main.ultipro;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.interfaces.IBy;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.factories.WebFactory;
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
        WebFactory web = new WebFactory(automationInfo);
        addEmployeeButton = (Button) web.create(Button.class, "#ctl00_btnAdd", getSwitchForContentFrame());
        employeeSearchBox = (TextBox) web.create(TextBox.class, "#GridView1_TextEntryFilterControlInputBox_0", getSwitchForContentFrame());
    }

    //Since all the elements on this page
    private ArrayList<IBy> getSwitchForContentFrame(){
        ArrayList<IBy> switchMechanism = new ArrayList<>();
        switchMechanism.add(By.CssSelector("iframe[id*=ContentFrame]"));
        return switchMechanism;
    }
}
