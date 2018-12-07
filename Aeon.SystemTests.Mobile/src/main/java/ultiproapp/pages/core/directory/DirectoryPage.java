package ultiproapp.pages.core.directory;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.elements.web.TextBox;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.directory.directoryfilterbarlistgroup.DirectoryFilterBarListGroupActions;
import ultiproapp.pages.core.directory.directorylistgroup.DirectoryListGroupActions;

public class DirectoryPage extends Page {

    private String parentPageSelector = "upa-directory-page:not([hidden]) ";

    public Button backButton;
    public Button filterButton;
    public TextBox searchInput;
    public Label titleLabel;
    public ListGroup<DirectoryListGroupActions> directoryList;
    public ListGroup<DirectoryFilterBarListGroupActions> directoryFilterBar;


    public DirectoryPage(AutomationInfo info) {

        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "ion-title"));
        filterButton = new Button(info, By.cssSelector(parentPageSelector + "ion-button[data-automation=\"search-bar-filter\"]"));
        searchInput = new TextBox(info, By.cssSelector(parentPageSelector + "ion-searchbar[data-automation=\"search-bar\"] div input"));
        directoryList = new ListGroup<>(info, By.cssSelector("upa-infinite-list ion-card"), new DirectoryListGroupActions());
        directoryFilterBar = new ListGroup<>(info, By.cssSelector("upa-filter-bar"), new DirectoryFilterBarListGroupActions());

    }

}
