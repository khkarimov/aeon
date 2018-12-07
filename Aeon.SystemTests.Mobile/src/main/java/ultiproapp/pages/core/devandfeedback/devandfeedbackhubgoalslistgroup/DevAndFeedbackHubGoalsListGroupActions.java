package ultiproapp.pages.core.devandfeedback.devandfeedbackhubgoalslistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DevAndFeedbackHubGoalsListGroupActions extends ListGroupActions<DevAndFeedbackHubGoalsListGroupActions, DevAndFeedbackHubGoalsListGroupElements> {

    public DevAndFeedbackHubGoalsListGroupActions() {

        super(DevAndFeedbackHubGoalsListGroupActions.class, DevAndFeedbackHubGoalsListGroupElements.class);
        this.rowSelector = "[data-automation=\"goals-item\"]";

    }

    public DevAndFeedbackHubGoalsListGroupActions goalProgress(String value) {

        return findRow(value, "[data-automation=\"goal-progress-label\"]");

    }

    public DevAndFeedbackHubGoalsListGroupActions goalTitle(String value) {

        return findRow(value, "[data-automation=\"goal-title-label\"]");

    }

}
