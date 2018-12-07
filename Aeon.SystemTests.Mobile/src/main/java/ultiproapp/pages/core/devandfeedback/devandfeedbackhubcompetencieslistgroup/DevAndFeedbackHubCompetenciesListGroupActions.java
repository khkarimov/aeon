package ultiproapp.pages.core.devandfeedback.devandfeedbackhubcompetencieslistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class DevAndFeedbackHubCompetenciesListGroupActions extends ListGroupActions<DevAndFeedbackHubCompetenciesListGroupActions, DevAndFeedbackHubCompetenciesListGroupElements> {

    public DevAndFeedbackHubCompetenciesListGroupActions() {

        super(DevAndFeedbackHubCompetenciesListGroupActions.class, DevAndFeedbackHubCompetenciesListGroupElements.class);
        this.rowSelector = "[data-automation=\"competency-item\"]";

    }

    public DevAndFeedbackHubCompetenciesListGroupActions competencyTitle(String value) {

        return findRow(value, "[data-automation=\"competency-title\"]");

    }

}
