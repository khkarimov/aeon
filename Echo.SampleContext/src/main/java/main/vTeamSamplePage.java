package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Button;
import echo.core.test_abstraction.elements.Checkbox;
import echo.core.test_abstraction.elements.FileDialogInput;
import echo.core.test_abstraction.elements.Image;

/**
 * Created by Administrator on 6/3/2016.
 */
public class vTeamSamplePage {
    private AutomationInfo info;
    public Button DisabledButton;
    public Button OpenAlertButton;
    public Image UltimateLogoImage;
    public Checkbox TestCheckbox;
    public FileDialogInput TestFileDialogInput;
    public Button Start;

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;

        DisabledButton = new Button(info, By.CssSelector("button[id='disabled-button']"));
        UltimateLogoImage = new Image(info, By.CssSelector("img[id='dragtarget']"));
        TestCheckbox = new Checkbox(info, By.CssSelector("input[id='checkbox']"));
        TestFileDialogInput = new FileDialogInput(info, By.CssSelector("input[id='file-dialog']"));
        OpenAlertButton = new Button(info, By.CssSelector("button[id='alertDialog']"));
        Start = new Button (info, By.CssSelector("button[id='start']"));
    }
}
