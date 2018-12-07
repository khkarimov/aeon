package ultiproapp.pages.core.pay.paystatementyearheaderlistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class PayStatementYearHeaderListGroupActions extends ListGroupActions<PayStatementYearHeaderListGroupActions, PayStatementYearHeaderListGroupElements> {

    public PayStatementYearHeaderListGroupActions() {

        super(PayStatementYearHeaderListGroupActions.class, PayStatementYearHeaderListGroupElements.class);
        this.rowSelector = "[data-automation=\"pay-year-header\"]";

    }

    public PayStatementYearHeaderListGroupActions payYearHeader(String value) {

        return findRow(value, "[data-automation=\"pay-year\"]");

    }

}
