package ultiproapp.pages.core.directory.directoryradiolistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DirectoryRadioListGroupActions extends ListGroupActions<DirectoryRadioListGroupActions, DirectoryRadioListGroupElements> {

    public DirectoryRadioListGroupActions() {
        super(DirectoryRadioListGroupActions.class, DirectoryRadioListGroupElements.class);
        this.rowSelector = "ion-item";

    }

    public DirectoryRadioListGroupActions label(String value) {
        return findRow(value, "ion-label[data-automation=\"label\"]");
    }

}
