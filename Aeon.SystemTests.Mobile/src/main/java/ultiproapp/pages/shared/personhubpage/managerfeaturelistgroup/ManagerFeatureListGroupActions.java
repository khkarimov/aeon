package ultiproapp.pages.shared.personhubpage.managerfeaturelistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class ManagerFeatureListGroupActions extends ListGroupActions<ManagerFeatureListGroupActions, ManagerFeatureListGroupElements> {

    public ManagerFeatureListGroupActions() {

        super(ManagerFeatureListGroupActions.class, ManagerFeatureListGroupElements.class);
        this.rowSelector = "[data-automation=\"manager-feature-item\"]";

    }

    public ManagerFeatureListGroupActions featureHeading(String value) {

        return findRow(value, "[data-automation=\"manager-feature-label\"] h2");

    }

}
