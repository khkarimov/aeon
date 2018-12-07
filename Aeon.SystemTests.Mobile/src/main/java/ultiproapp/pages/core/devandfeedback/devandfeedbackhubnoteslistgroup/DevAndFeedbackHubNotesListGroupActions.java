package ultiproapp.pages.core.devandfeedback.devandfeedbackhubnoteslistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DevAndFeedbackHubNotesListGroupActions extends ListGroupActions<DevAndFeedbackHubNotesListGroupActions, DevAndFeedbackHubNotesListGroupElements> {

    public DevAndFeedbackHubNotesListGroupActions() {

        super(DevAndFeedbackHubNotesListGroupActions.class, DevAndFeedbackHubNotesListGroupElements.class);
        this.rowSelector = "[data-automation=\"note-item\"]";

    }

    public DevAndFeedbackHubNotesListGroupActions creatorName(String value) {

        return findRow(value, "[data-automation=\"note-label\"] h2");

    }

    public DevAndFeedbackHubNotesListGroupActions comment(String value) {

        return findRow(value, "[data-automation=\"note-label\"] p");

    }

    public DevAndFeedbackHubNotesListGroupActions date(String value) {

        return findRow(value, "[data-automation=\"note-date\"]");

    }

}
