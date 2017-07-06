package main;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;
import main.samplegrid.MyGrid;
import main.samplegrid.MyGridHeaders;

/**
 * Created by Administrator on 6/3/2016.
 */
public class VTeamSamplePage {
    private AutomationInfo info;
    // Grid selector
    private final By gridSelector = By.cssSelector("#grid-table-id");

    public Button disabledButton;
    public Button openAlertButton;
    public Image ultimateLogoImage;
    public Image HeatLogoImage;
    public Image ultimateLogoImageDoubleClick;
    public Checkbox testCheckbox;
    public FileDialogInput testFileDialogInput;
    public Button start;
    public TextBox startButtonColorRed;
    public TextBox alertTitleTextBox;
    public ListItem draggableListItem;
    public Label checkboxLabel;
    public Link tryLink;
    public Select dropDown;
    public Checkbox checkbox100;
    public WebElement divWindow;
    public Label bodyTag;
    public WebElement div;
    public Button popupButton;
    public Select lexoDropDown;
    public Select revLexoDropDown;
    public Label dateLabel;
    public Label reactionLabel;
    public Button invisibleButton;
    public Label nonExistentLabel;
    public TextBox formTextBox;
    public RadioButton nextRadioButton;
    public Button smileyFace1;
    public MyGrid myGrid;

    public VTeamSamplePage(AutomationInfo info) {
        disabledButton = new Button(info, By.cssSelector("button[id='disabled-button']"));
        HeatLogoImage = new Image(info, By.cssSelector("img[src='HeatLogo.jpg']"));
        ultimateLogoImage = new Image(info, By.cssSelector("img[id='dragtarget']"));
        ultimateLogoImageDoubleClick = new Image(info, By.cssSelector("img[id='dbl-click-image']"));
        testCheckbox = new Checkbox(info, By.cssSelector("input[id='checkbox']"));
        testFileDialogInput = new FileDialogInput(info, By.cssSelector("input[id='file-dialog']"));
        openAlertButton = new Button(info, By.cssSelector("button[id='alertDialog']"));
        alertTitleTextBox = new TextBox(info, By.cssSelector("input[id ='sample1']"));
        start = new Button(info, By.cssSelector("button[id='start']"));
        checkboxLabel = new Label(info, By.cssSelector("label[for='checkbox']"));
        tryLink = new Link(info, By.cssSelector("a[id='tryLink']"));
        dropDown = new Select(info, By.cssSelector("select[id='drop-down-list']"));

        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        draggableListItem = new ListItem(info, By.cssSelector("li[id='node1']"));
        checkbox100 = new Checkbox(info, By.cssSelector("input[id='checkbox100']"));
        div = new WebElement(info, By.cssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
        popupButton = new Button(info, By.cssSelector("a[id='popup-button']"));
        lexoDropDown = new Select(info, By.cssSelector("select[id='lexicographic-drop-down']"));
        revLexoDropDown = new Select(info, By.cssSelector("select[id='lexicographic-drop-down-rev']"));
        dateLabel = new Label(info, By.cssSelector("#date-text"));
        reactionLabel = new Label(info, By.cssSelector("#reaction-text"));
        invisibleButton = new Button(info, By.cssSelector("#invisible-button"));
        nonExistentLabel = new Label(info, By.cssSelector("fakeSelector"));
        formTextBox = new TextBox(info, By.cssSelector("#sample1"));
        nextRadioButton = new RadioButton(info, By.cssSelector("#next-radio-button"));
        smileyFace1 = new Button(info, By.cssSelector(".call-1 > button:nth-child(1)"));

        // The reason the grid selector is passed to the MyGridHeaders class instead of the grid is because the MyGridHeaders class
        // uses the grid selector as a base to navigate through the html table. We can change this and instantiate and instance of
        // MyGridHeaders inside the MyGrid class to avoid confusion.
        myGrid = new MyGrid(new MyGridHeaders(info, gridSelector));
        divWindow = new WebElement(info, By.cssSelector("div[id='drop-div']"));
        bodyTag = new Label(info, By.cssSelector("label[for='sample5']"));
    }
}
