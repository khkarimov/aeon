package main.sample.pages;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.*;
import aeon.core.testabstraction.models.Page;
import main.sample.samplegrid.MyGrid;
import main.sample.samplegrid.MyGridHeaders;
import main.sample.samplelistgroup.MyListGroup;
import main.sample.samplelistgroup.MyListGroupActions;

/**
 * Model of the sample HTML file for system tests.
 */
public class VTeamSamplePage extends Page {

    // Grid selector
    private final By gridSelector = By.cssSelector("#grid-table-id");

    public Button disabledButton;
    public Button openAlertButton;
    public Image ultimateLogoImage;
    public Image heatLogoImage;
    public Image ultimateLogoImageDoubleClick;
    public Checkbox testCheckbox;
    public FileDialogInput testFileDialogInput;
    public Button start;
    public TextBox alertTitleTextBox;
    public ListItem draggableListItem;
    public ListItem draggedListItem;
    public Link tryLink;
    public Select dropDown;
    public Checkbox checkbox100;
    public WebElement divWindow;
    public Label bodyTag;
    public WebElement div;
    public Button popupButton;
    public Select lexoDropDown;
    public Select revLexoDropDown;
    public Label dateSelector;
    public Label dateLabel;
    public Label reactionLabel;
    public Button invisibleButton;
    public Label nonExistentLabel;
    public TextBox formTextBox;
    public RadioButton nextRadioButton;
    public Button smileyFace1;
    public Button smileyFace2;
    public Button smileyFace3;
    public MyGrid myGrid;
    public MyListGroup myListGroup;

    /**
     * Constructor.
     *
     * @param info The automation info object to use.
     */
    public VTeamSamplePage(AutomationInfo info) {
        disabledButton = new Button(info, By.cssSelector("button[id='disabled-button']"));
        heatLogoImage = new Image(info, By.cssSelector("img[src='HeatLogo.jpg']"));
        ultimateLogoImage = new Image(info, By.cssSelector("img[id='dragtarget']"));
        ultimateLogoImageDoubleClick = new Image(info, By.cssSelector("img[id='dbl-click-image']"));
        testCheckbox = new Checkbox(info, By.cssSelector("input[id='checkbox']"));
        testFileDialogInput = new FileDialogInput(info, By.cssSelector("input[id='file-dialog']"));
        openAlertButton = new Button(info, By.cssSelector("button[id='alertDialog']"));
        alertTitleTextBox = new TextBox(info, By.cssSelector("input[id ='sample1']"));
        start = new Button(info, By.cssSelector("button[id='start']"));
        tryLink = new Link(info, By.cssSelector("a[id='tryLink']"));
        dropDown = new Select(info, By.cssSelector("select[id='drop-down-list']"));

        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        draggableListItem = new ListItem(info, By.cssSelector("li[id='node1']"));
        draggedListItem = new ListItem(info, By.cssSelector("ul[id='box2'] li[id='node1']"));

        checkbox100 = new Checkbox(info, By.cssSelector("input[id='checkbox100']"));
        div = new WebElement(info, By.cssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
        popupButton = new Button(info, By.cssSelector("a[id='popup-button']"));
        lexoDropDown = new Select(info, By.cssSelector("select[id='lexicographic-drop-down']"));
        revLexoDropDown = new Select(info, By.cssSelector("select[id='lexicographic-drop-down-rev']"));
        dateSelector = new Label(info, By.cssSelector("#date-selector"));
        dateLabel = new Label(info, By.cssSelector("#date-text"));
        reactionLabel = new Label(info, By.cssSelector("#reaction-text"));
        invisibleButton = new Button(info, By.cssSelector("#invisible-button"));
        nonExistentLabel = new Label(info, By.cssSelector("fakeSelector"));
        formTextBox = new TextBox(info, By.cssSelector("#sample1"));
        nextRadioButton = new RadioButton(info, By.cssSelector("#next-radio-button"));
        smileyFace1 = new Button(info, By.cssSelector(".call-1 > button:nth-child(1)"));
        smileyFace2 = new Button(info, By.cssSelector(".call-2 > button:nth-child(1)"));
        smileyFace3 = new Button(info, By.cssSelector(".call-3 > button:nth-child(1)"));

        myGrid = new MyGrid(info, gridSelector, new MyGridHeaders());
        divWindow = new WebElement(info, By.cssSelector("div[id='drop-div']"));
        bodyTag = new Label(info, By.cssSelector("label[for='sample5']"));
        myListGroup = new MyListGroup(info, By.cssSelector(".demo-list-three"), new MyListGroupActions());
    }
}
