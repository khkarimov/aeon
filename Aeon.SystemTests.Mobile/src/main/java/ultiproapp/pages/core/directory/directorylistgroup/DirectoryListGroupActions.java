package ultiproapp.pages.core.directory.directorylistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DirectoryListGroupActions extends ListGroupActions<DirectoryListGroupActions, DirectoryListGroupElements> {

    public DirectoryListGroupActions() {

        super(DirectoryListGroupActions.class, DirectoryListGroupElements.class);
        this.rowSelector = "upa-employee-list-item";

    }

    public DirectoryListGroupActions name(String value) {

        return findRow(value, "h2[data-automation=\"employee-name\"]");

    }

    public DirectoryListGroupActions title(String value) {

        return findRow(value, "p[data-automation=\"employee-title\"]");

    }

}
