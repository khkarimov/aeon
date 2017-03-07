package main;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.test_abstraction.elements.web.*;
import main.samplegrid.MyGrid;
import main.samplegrid.MyGridHeaders;

/**
 * Created by Administrator on 6/3/2016.
 */
public class VTeamSamplePage {
    private AutomationInfo info;
    // Grid selector
    private final By gridSelector = By.CssSelector("#grid-table-id");

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
    public WebElement DivWindow;
    public Label BodyTag;
    public WebElement div;
    public Button PopupButton;
    public Select LexoDropDown;
    public Select RevLexoDropDown;
    public Label DateLabel;
    public Button InvisibleButton;
    public Label NonExistentLabel;
    public TextBox FormTextBox;
    public RadioButton NextRadioButton;
    public Button SmileyFace1;
    public MyGrid myGrid;

    public VTeamSamplePage(AutomationInfo info) {
        DisabledButton = new Button(info, By.CssSelector( "button[id='disabled-button']"));
        UltimateLogoImage = new Image(info, By.CssSelector("img[id='dragtarget']"));
        TestCheckbox = new Checkbox(info, By.CssSelector( "input[id='checkbox']"));
        TestFileDialogInput = new FileDialogInput(info, By.CssSelector("input[id='file-dialog']"));
        OpenAlertButton = new Button(info, By.CssSelector("button[id='alertDialog']"));
        AlertTitleTextBox = new TextBox(info, By.CssSelector("input[id ='sample1']"));
        Start = new Button(info, By.CssSelector( "button[id='start']"));
        CheckboxLabel = new Label(info, By.CssSelector("label[for='checkbox']"));
        TryLink = new Link(info, By.CssSelector( "a[id='tryLink']"));
        DropDown = new Select(info, By.CssSelector("select[id='drop-down-list']"));
        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        DraggableListItem = new ListItem(info, By.CssSelector("li[id='node1']"));
        Checkbox100 = new Checkbox(info, By.CssSelector("input[id='checkbox100']"));
        div = new WebElement(info, By.CssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
        PopupButton = new Button(info, By.CssSelector("a[id='popup-button']"));
        LexoDropDown = new Select(info, By.CssSelector("select[id='lexicographic-drop-down']"));
        RevLexoDropDown = new Select(info, By.CssSelector("select[id='lexicographic-drop-down-rev']"));
        DateLabel = new Label(info, By.CssSelector("#date-text"));
        InvisibleButton = new Button(info, By.CssSelector("#invisible-button"));
        NonExistentLabel = new Label(info, By.CssSelector("fakeSelector"));
        FormTextBox = new TextBox(info, By.CssSelector("#sample1"));
        NextRadioButton = new RadioButton(info, By.CssSelector("#next-radio-button"));
        SmileyFace1 = new Button(info, By.CssSelector(".call-1 > button:nth-child(1)"));
        // The reason the grid selector is passed to the MyGridHeaders class instead of the grid is because the MyGridHeaders class
        // uses the grid selector as a base to navigate through the html table. We can change this and instantiate and instance of
        // MyGridHeaders inside the MyGrid class to avoid confusion.
        myGrid = new MyGrid(new MyGridHeaders(info, gridSelector));
        DivWindow = new WebElement(info, By.CssSelector("div[id='drop-div']"));
        BodyTag = new Label(info, By.CssSelector("label[for='sample5']"));
    }
}
