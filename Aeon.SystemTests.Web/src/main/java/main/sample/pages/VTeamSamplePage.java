package main.sample.pages;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.*;
import com.ultimatesoftware.aeon.core.testabstraction.models.Page;
import main.sample.samplegrid.MaterialTable;
import main.sample.samplegrid.MaterialTableContainer;
import main.sample.samplelistgroup.ActorList;
import main.sample.samplelistgroup.ActorListContainer;

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
    public ListItem draggableHTML5Item;
    public ListItem draggedHTML5Item;
    public Link tryLink;
    public Dropdown dropDown;
    public Checkbox checkbox100;
    public WebElement divWindow;
    public Label bodyTag;
    public WebElement div;
    public Button popupButton;
    public Dropdown lexoDropDown;
    public Dropdown revLexoDropDown;
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
    public MaterialTableContainer materialTableContainer;
    public ActorListContainer actorListContainer;

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     */
    public VTeamSamplePage(AutomationInfo automationInfo) {
        disabledButton = new Button(automationInfo, By.cssSelector("button[id='disabled-button']"));
        heatLogoImage = new Image(automationInfo, By.cssSelector("img[src='HeatLogo.jpg']"));
        ultimateLogoImage = new Image(automationInfo, By.cssSelector("img[id='dragtarget']"));
        ultimateLogoImageDoubleClick = new Image(automationInfo, By.cssSelector("img[id='dbl-click-image']"));
        testCheckbox = new Checkbox(automationInfo, By.cssSelector("input[id='checkbox']"));
        testFileDialogInput = new FileDialogInput(automationInfo, By.cssSelector("input[id='file-dialog']"));
        openAlertButton = new Button(automationInfo, By.cssSelector("button[id='alertDialog']"));
        alertTitleTextBox = new TextBox(automationInfo, By.cssSelector("input[id ='sample1']"));
        start = new Button(automationInfo, By.cssSelector("button[id='start']"));
        tryLink = new Link(automationInfo, By.cssSelector("a[id='tryLink']"));
        dropDown = new Dropdown(automationInfo, By.cssSelector("select[id='drop-down-list']"));

        //SR - this is not part of our sample site but I'm using it to test the drag and drop command
        draggableListItem = new ListItem(automationInfo, By.cssSelector("li[id='node1']"));
        draggedListItem = new ListItem(automationInfo, By.cssSelector("ul[id='box2'] li[id='node1']"));
        draggableHTML5Item = new ListItem(automationInfo, By.cssSelector("[id='drag1']"));
        draggedHTML5Item = new ListItem(automationInfo, By.cssSelector("[id='div2'] [id='drag1']"));

        checkbox100 = new Checkbox(automationInfo, By.cssSelector("input[id='checkbox100']"));
        div = new WebElement(automationInfo, By.cssSelector("div.demo-container:nth-child(1) > div:nth-child(2)"));
        popupButton = new Button(automationInfo, By.cssSelector("a[id='popup-button']"));
        lexoDropDown = new Dropdown(automationInfo, By.cssSelector("select[id='lexicographic-drop-down']"));
        revLexoDropDown = new Dropdown(automationInfo, By.cssSelector("select[id='lexicographic-drop-down-rev']"));
        dateSelector = new Label(automationInfo, By.cssSelector("#date-selector"));
        dateLabel = new Label(automationInfo, By.cssSelector("#date-text"));
        reactionLabel = new Label(automationInfo, By.cssSelector("#reaction-text"));
        invisibleButton = new Button(automationInfo, By.cssSelector("#invisible-button"));
        nonExistentLabel = new Label(automationInfo, By.cssSelector("fakeSelector"));
        formTextBox = new TextBox(automationInfo, By.cssSelector("#sample1"));
        nextRadioButton = new RadioButton(automationInfo, By.cssSelector("#next-radio-button"));
        smileyFace1 = new Button(automationInfo, By.cssSelector(".call-1 > button:nth-child(1)"));
        smileyFace2 = new Button(automationInfo, By.cssSelector(".call-2 > button:nth-child(1)"));
        smileyFace3 = new Button(automationInfo, By.cssSelector(".call-3 > button:nth-child(1)"));

        materialTableContainer = new MaterialTableContainer(automationInfo, gridSelector, new MaterialTable());
        divWindow = new WebElement(automationInfo, By.cssSelector("div[id='drop-div']"));
        bodyTag = new Label(automationInfo, By.cssSelector("label[for='sample5']"));
        actorListContainer = new ActorListContainer(automationInfo, By.cssSelector(".demo-list-three"), new ActorList());
    }
}
