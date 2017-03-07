package main;

import aeon.core.command_execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import main.samplegrid.MyGrid;
import main.samplegrid.MyGridHeaders;

/**
 * Created by Administrator on 6/3/2016.
 */
public class VTeamSamplePage {
    private AutomationInfo info;
    // Grid selector
    private final By gridSelector = By.CssSelector("#grid-table-id");

    public aeon.core.test_abstraction.elements.web.Button DisabledButton;
    public aeon.core.test_abstraction.elements.web.Button OpenAlertButton;
    public aeon.core.test_abstraction.elements.web.Image UltimateLogoImage;
    public aeon.core.test_abstraction.elements.web.Checkbox TestCheckbox;
    public aeon.core.test_abstraction.elements.web.FileDialogInput TestFileDialogInput;
    public aeon.core.test_abstraction.elements.web.Button Start;
    public aeon.core.test_abstraction.elements.web.TextBox AlertTitleTextBox;
    public aeon.core.test_abstraction.elements.web.ListItem DraggableListItem;
    public aeon.core.test_abstraction.elements.web.Label CheckboxLabel;
    public aeon.core.test_abstraction.elements.web.Link TryLink;
    public aeon.core.test_abstraction.elements.web.Select DropDown;
    public aeon.core.test_abstraction.elements.web.Checkbox Checkbox100;
    public aeon.core.test_abstraction.elements.web.WebElement DivWindow;
    public aeon.core.test_abstraction.elements.web.Label BodyTag;
    public aeon.core.test_abstraction.elements.web.WebElement div;
    public aeon.core.test_abstraction.elements.web.Button PopupButton;
    public aeon.core.test_abstraction.elements.web.Select LexoDropDown;
    public aeon.core.test_abstraction.elements.web.Select RevLexoDropDown;
    public aeon.core.test_abstraction.elements.web.Label DateLabel;
    public aeon.core.test_abstraction.elements.web.Button InvisibleButton;
    public aeon.core.test_abstraction.elements.web.Label NonExistentLabel;
    public aeon.core.test_abstraction.elements.web.TextBox FormTextBox;
    public aeon.core.test_abstraction.elements.web.RadioButton NextRadioButton;
    public aeon.core.test_abstraction.elements.web.Button SmileyFace1;
    public MyGrid myGrid;

    public VTeamSamplePage(AutomationInfo info) {
        DisabledButton = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector( "button[id='disabled-button']"));
        UltimateLogoImage = new aeon.core.test_abstraction.elements.web.Image(info, By.CssSelector("img[id='dragtarget']"));
        TestCheckbox = new aeon.core.test_abstraction.elements.web.Checkbox(info, By.CssSelector( "input[id='checkbox']"));
        TestFileDialogInput = new aeon.core.test_abstraction.elements.web.FileDialogInput(info, By.CssSelector("input[id='file-dialog']"));
        OpenAlertButton = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector("button[id='alertDialog']"));
        AlertTitleTextBox = new aeon.core.test_abstraction.elements.web.TextBox(info, By.CssSelector("input[id ='sample1']"));
        Start = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector( "button[id='start']"));
        CheckboxLabel = new aeon.core.test_abstraction.elements.web.Label(info, By.CssSelector("label[for='checkbox']"));
        TryLink = new aeon.core.test_abstraction.elements.web.Link(info, By.CssSelector( "a[id='tryLink']"));
        DropDown = new aeon.core.test_abstraction.elements.web.Select(info, By.CssSelector("select[id='drop-down-list']"));
        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        DraggableListItem = new aeon.core.test_abstraction.elements.web.ListItem(info, By.CssSelector("li[id='node1']"));
        Checkbox100 = new aeon.core.test_abstraction.elements.web.Checkbox(info, By.CssSelector("input[id='checkbox100']"));
        div = new aeon.core.test_abstraction.elements.web.WebElement(info, By.CssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
        PopupButton = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector("a[id='popup-button']"));
        LexoDropDown = new aeon.core.test_abstraction.elements.web.Select(info, By.CssSelector("select[id='lexicographic-drop-down']"));
        RevLexoDropDown = new aeon.core.test_abstraction.elements.web.Select(info, By.CssSelector("select[id='lexicographic-drop-down-rev']"));
        DateLabel = new aeon.core.test_abstraction.elements.web.Label(info, By.CssSelector("#date-text"));
        InvisibleButton = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector("#invisible-button"));
        NonExistentLabel = new aeon.core.test_abstraction.elements.web.Label(info, By.CssSelector("fakeSelector"));
        FormTextBox = new aeon.core.test_abstraction.elements.web.TextBox(info, By.CssSelector("#sample1"));
        NextRadioButton = new aeon.core.test_abstraction.elements.web.RadioButton(info, By.CssSelector("#next-radio-button"));
        SmileyFace1 = new aeon.core.test_abstraction.elements.web.Button(info, By.CssSelector(".call-1 > button:nth-child(1)"));
        // The reason the grid selector is passed to the MyGridHeaders class instead of the grid is because the MyGridHeaders class
        // uses the grid selector as a base to navigate through the html table. We can change this and instantiate and instance of
        // MyGridHeaders inside the MyGrid class to avoid confusion.
        myGrid = new MyGrid(new MyGridHeaders(info, gridSelector));
        DivWindow = new aeon.core.test_abstraction.elements.web.WebElement(info, By.CssSelector("div[id='drop-div']"));
        BodyTag = new aeon.core.test_abstraction.elements.web.Label(info, By.CssSelector("label[for='sample5']"));
    }
}
