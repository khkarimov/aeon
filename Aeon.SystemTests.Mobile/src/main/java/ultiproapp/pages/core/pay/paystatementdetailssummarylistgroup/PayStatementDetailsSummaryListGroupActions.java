package ultiproapp.pages.core.pay.paystatementdetailssummarylistgroup;

import aeon.core.testabstraction.elements.web.ListGroupActions;

public class PayStatementDetailsSummaryListGroupActions extends ListGroupActions<PayStatementDetailsSummaryListGroupActions, PayStatementDetailsSummaryListGroupElements> {

    public PayStatementDetailsSummaryListGroupActions() {

        super(PayStatementDetailsSummaryListGroupActions.class, PayStatementDetailsSummaryListGroupElements.class);
        this.rowSelector = "ion-item";

    }

    public PayStatementDetailsSummaryListGroupActions title(String value) {

        return findRow(value, "ion-label p");

    }

    public PayStatementDetailsSummaryListGroupActions value(String value) {

        return findRow(value, "ion-label h2");

    }

}
