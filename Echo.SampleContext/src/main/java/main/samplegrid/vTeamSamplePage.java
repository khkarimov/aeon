package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.*;
import echo.core.test_abstraction.elements.Button;

import java.awt.*;

/**
 * Created by Administrator on 6/3/2016.
 */
public class vTeamSamplePage {
    private AutomationInfo info;
    public Button DisabledButton;
    public Button OpenAlertButton;
    public Image UltimateLogoImage;
    public Checkbox TestCheckbox;
    public Element element;

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;

        element = new Element( By.CssSelector("button[id='start']"), info);

//        DisabledButton = new Button(info, By.CssSelector("button[id='disabled-button']"));
//        UltimateLogoImage = new Image(info, By.CssSelector("img[id='dragtarget']"));
//        TestCheckbox = new Checkbox(info, By.CssSelector("input[id='checkbox']"));
//        TestFileDialogInput = new FileDialogInput(info, By.CssSelector("input[id='file-dialog']"));
//        OpenAlertButton = new Button(info, By.CssSelector("button[id='alertDialog']"));
        //SR - THIS IS NOT PART OF THE SAMPLE PAGE, ITS ONLY USED TO TEST DRAG AND DROP
        // This context belongs to another site: http://www.dhtmlgoodies.com/scripts/drag-drop-nodes/drag-drop-nodes-demo2.html
//        ListItem = new ListItem(info, By.CssSelector("li[id='node1']"));
    }
}