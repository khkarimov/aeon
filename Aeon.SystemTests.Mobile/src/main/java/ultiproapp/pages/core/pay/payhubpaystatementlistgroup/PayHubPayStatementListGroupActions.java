package ultiproapp.pages.core.pay.payhubpaystatementlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class PayHubPayStatementListGroupActions extends ListGroupActions<PayHubPayStatementListGroupActions, PayHubPayStatementListGroupElements> {

    public PayHubPayStatementListGroupActions() {

        super(PayHubPayStatementListGroupActions.class, PayHubPayStatementListGroupElements.class);
        this.rowSelector = "[data-automation=\"pay-slide\"]";

    }

    public PayHubPayStatementListGroupActions netPay(String value) {

        return findRow(value, "[data-automation=\"net-pay\"]");

    }

    public PayHubPayStatementListGroupActions totalHours(String value) {

        return findRow(value, "[data-automation=\"pay-total-hours\"]");

    }

    public PayHubPayStatementListGroupActions date(String value) {

        return findRow(value, "[data-automation=\"pay-date\"]");

    }

}
