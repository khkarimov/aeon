package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.test_abstraction.elements.web.Button;
import echo.core.test_abstraction.elements.web.Checkbox;
import echo.core.test_abstraction.elements.web.FileDialogInput;
import echo.core.test_abstraction.elements.web.Image;
import echo.core.test_abstraction.elements.web.TextBox;
import echo.core.test_abstraction.elements.factories.WebFactory;

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
    public TextBox AlertTitleTextBox;

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;
        WebFactory web = new WebFactory(this.info);
        DisabledButton = (Button) web.create(Button.class, "button[id='disabled-button']");
        UltimateLogoImage = (Image) web.create(Image.class, "img[id='dragtarget']");
        TestCheckbox = (Checkbox) web.create(Checkbox.class, "input[id='checkbox']");
        TestFileDialogInput = (FileDialogInput) web.create(FileDialogInput.class, "input[id='file-dialog']");
        OpenAlertButton = (Button) web.create(Button.class, "button[id='alertDialog']");
        AlertTitleTextBox = (TextBox) web.create(TextBox.class, "input[id ='sample1'");

        Start = (Button) web.create(Button.class, "button[id='start']");
    }
}
