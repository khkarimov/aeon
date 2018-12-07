package ultiproapp.pages.core.pay.paystatementlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class PayStatementListGroupActions extends ListGroupActions<PayStatementListGroupActions, PayStatementListGroupElements> {

    public PayStatementListGroupActions() {

        super(PayStatementListGroupActions.class, PayStatementListGroupElements.class);
        this.rowSelector = "[data-automation=\"pay-statement\"]";

    }

    public PayStatementListGroupActions netPay(String value) {

        return findRow(value, "[data-automation=\"net-pay\"]");

    }

    public PayStatementListGroupActions totalHours(String value) {

        return findRow(value, "[data-automation=\"total-hours\"]");

    }

    public PayStatementListGroupActions companyName(String value) {

        return findRow(value, "[data-automation=\"company-name\"]");

    }

    public PayStatementListGroupActions date(String value) {

        return findRow(value, "[data-automation=\"pay-date\"]");

    }

}
