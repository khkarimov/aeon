package ultiproapp.pages.core.directory.directoryfilterlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DirectoryFilterListGroupActions extends ListGroupActions<DirectoryFilterListGroupActions, DirectoryFilterListGroupElements> {

    public DirectoryFilterListGroupActions() {

        super(DirectoryFilterListGroupActions.class, DirectoryFilterListGroupElements.class);
        this.rowSelector = "ion-item:not([hidden])[data-automation=\"filter-item\"]";

    }

    public DirectoryFilterListGroupActions label(String value) {

        return findRow(value, "h2[data-automation=\"title\"]");

    }

    public DirectoryFilterListGroupActions sublabel(String value) {

        return findRow(value, "p");

    }

}
