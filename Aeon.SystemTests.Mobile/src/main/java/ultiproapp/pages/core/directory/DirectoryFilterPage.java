package ultiproapp.pages.core.directory;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.directory.directoryfilterlistgroup.DirectoryFilterListGroupActions;
import ultiproapp.pages.core.directory.directoryradiolistgroup.DirectoryRadioListGroupActions;

public class DirectoryFilterPage extends Page {

    private String parentPageSelector = "upa-filter-page:not([hidden]) ";

    public Button closeButton;
    public Button clearAllButton;
    public Button doneButton;
    public Button radioBackButton;
    public Button radioDoneButton;
    public ListGroup<DirectoryFilterListGroupActions> directoryFilterList;
    public ListGroup<DirectoryRadioListGroupActions> directoryRadioList;


    public DirectoryFilterPage(AutomationInfo info) {

        closeButton = new Button(info, By.cssSelector(parentPageSelector + "ion-button[data-automation=\"filter-close\"]"));
        clearAllButton = new Button(info, By.cssSelector(parentPageSelector + "ion-button[data-automation=\"filter-clear-all\"]"));
        doneButton = new Button(info, By.cssSelector(parentPageSelector + "ion-button[data-automation=\"filter-done\"]"));
        radioBackButton = new Button(info, By.cssSelector("upa-filter-options:not([hidden]) " + "ion-buttons[slot=\"start\"] ion-button"));
        radioDoneButton = new Button(info, By.cssSelector("upa-filter-options:not([hidden]) " + "ion-buttons[slot=\"end\"] ion-button"));
        directoryFilterList = new ListGroup<>(info, By.cssSelector("ion-list"), new DirectoryFilterListGroupActions());
        directoryRadioList = new ListGroup<>(info, By.cssSelector("ion-list"), new DirectoryRadioListGroupActions());

    }
}
