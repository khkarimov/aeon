package ultiproapp.pages.shared.teampages.teamfeaturelistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class TeamFeatureListGroupActions extends ListGroupActions<TeamFeatureListGroupActions, TeamFeatureListGroupElements> {

    public TeamFeatureListGroupActions() {

        super(TeamFeatureListGroupActions.class, TeamFeatureListGroupElements.class);
        this.rowSelector = "[data-automation=\"team-feature-list-item\"]";

    }

    public TeamFeatureListGroupActions featureHeading(String value) {

        return findRow(value, "[data-automation=\"team-feature-label\"] h2");

    }

    public TeamFeatureListGroupActions featureSubHeading(String value) {

        return findRow(value, "[data-automation=\"team-feature-label\"] p");

    }

}
