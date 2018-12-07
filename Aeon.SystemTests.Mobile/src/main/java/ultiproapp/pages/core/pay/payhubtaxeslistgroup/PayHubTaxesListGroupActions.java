package ultiproapp.pages.core.pay.payhubtaxeslistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class PayHubTaxesListGroupActions extends ListGroupActions<PayHubTaxesListGroupActions, PayHubTaxesListGroupElements> {

    public PayHubTaxesListGroupActions() {

        super(PayHubTaxesListGroupActions.class, PayHubTaxesListGroupElements.class);
        this.rowSelector = "[data-automation=\"tax-item\"]";

    }

    public PayHubTaxesListGroupActions taxFormType(String value) {

        return findRow(value, "[data-automation=\"tax-form-type\"]");

    }

    public PayHubTaxesListGroupActions taxFormInfo(String value) {

        return findRow(value, "[data-automation=\"tax-form-info\"]");

    }

}
