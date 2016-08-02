package main;

import echo.core.command_execution.AutomationInfo;
import echo.core.common.web.selectors.By;
import echo.core.test_abstraction.elements.Element;
import echo.core.test_abstraction.elements.web.*;
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
    public ListItem DraggableListItem;
    public Label CheckboxLabel;
    public Link TryLink;
    public Select DropDown;
    public Checkbox Checkbox100;
    public  WebElement div;

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;
        WebFactory web = new WebFactory(this.info);
        DisabledButton = (Button) web.create(Button.class, "button[id='disabled-button']");
        UltimateLogoImage = (Image) web.create(Image.class, "img[id='dragtarget']");
        TestCheckbox = (Checkbox) web.create(Checkbox.class, "input[id='checkbox']");
        TestFileDialogInput = (FileDialogInput) web.create(FileDialogInput.class, "input[id='file-dialog']");
        OpenAlertButton = (Button) web.create(Button.class, "button[id='alertDialog']");
        AlertTitleTextBox = (TextBox) web.create(TextBox.class, "input[id ='sample1']");
        Start =(Button) web.create(Button.class,"button[id='start']");
        CheckboxLabel = (Label) web.create(Label.class, "label[for='checkbox']");
        TryLink = (Link) web.create(Link.class, "a[id='tryLink']");
        DropDown = (Select) web.create(Select.class, "select[id='drop-down-list']");
        //SR - this is not part of our sample site but I'm using it to test the drag and drop command

    public vTeamSamplePage(AutomationInfo info){
        this.info = info;
        WebFactory web = new WebFactory(this.info);
        DisabledButton = (Button) web.create(Button.class, "button[id='disabled-button']");
        UltimateLogoImage = (Image) web.create(Image.class, "img[id='dragtarget']");
        TestCheckbox = (Checkbox) web.create(Checkbox.class, "input[id='checkbox']");
        TestFileDialogInput = (FileDialogInput) web.create(FileDialogInput.class, "input[id='file-dialog']");
        OpenAlertButton = (Button) web.create(Button.class, "button[id='alertDialog']");
        AlertTitleTextBox = (TextBox) web.create(TextBox.class, "input[id ='sample1']");
        Start =(Button) web.create(Button.class,"button[id='start']");
        CheckboxLabel = (Label) web.create(Label.class, "label[for='checkbox']");
        TryLink = (Link) web.create(Link.class, "a[id='tryLink']");
        DropDown = (Select) web.create(Select.class, "select[id='drop-down-list']");
        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        DraggableListItem = (ListItem) web.create(ListItem.class,"li[id='dragtarget']");
        DraggableListItem = (ListItem) web.create(ListItem.class,"li[id='node1']");
        Checkbox100 = (Checkbox) web.create(Checkbox.class, "input[id='checkbox100']");
        //TextBox Async = (TextBox) web.create(TextBox.class,"h2[id='form']");
        div = new WebElement(info, By.CssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
    }
}
