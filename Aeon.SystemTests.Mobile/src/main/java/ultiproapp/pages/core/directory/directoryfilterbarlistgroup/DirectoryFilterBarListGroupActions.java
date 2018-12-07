package ultiproapp.pages.core.directory.directoryfilterbarlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DirectoryFilterBarListGroupActions extends ListGroupActions<DirectoryFilterBarListGroupActions, DirectoryFilterBarListGroupElements> {

    public DirectoryFilterBarListGroupActions() {

        super(DirectoryFilterBarListGroupActions.class, DirectoryFilterBarListGroupElements.class);
        this.rowSelector = "ion-chip[data-automation=\"chip\"]";

    }

    public DirectoryFilterBarListGroupActions label(String value) {

        return findRow(value, "ion-label[data-automation=\"label\"]");

    }

}
