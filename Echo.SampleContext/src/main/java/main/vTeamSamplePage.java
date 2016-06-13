package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.Checkbox;
import echo.core.test_abstraction.elements.web.FileDialogInput;
import echo.core.test_abstraction.elements.web.Image;
import echo.core.test_abstraction.elements.web.TextBox;
import echo.core.test_abstraction.elements.web.WebFactory;

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
    public TextBox AlertTitleTextBox;

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;
        DisabledButton = WebFactory.createButton("button[id='disabled-button']", info);
        UltimateLogoImage = WebFactory.createImage("img[id='dragtarget']", info);
        TestCheckbox = WebFactory.createCheckbox("input[id='checkbox']", info);
        TestFileDialogInput = WebFactory.createFileDialogInput("input[id='file-dialog']", info);
        OpenAlertButton = WebFactory.createButton("button[id='alertDialog']", info);
        AlertTitleTextBox = WebFactory.createTextBox("input[id ='sample1'", info);

    }
}
