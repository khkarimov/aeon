package ultiproapp.pages.core.pay;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroup;
import aeon.core.testabstraction.models.Page;
import ultiproapp.pages.core.pay.paystatementlistgroup.PayStatementListGroupActions;
import ultiproapp.pages.core.pay.paystatementyearheaderlistgroup.PayStatementYearHeaderListGroupActions;

public class PayStatementPage extends Page {

    private String parentPageSelector = "upa-pay-statements:not([hidden]) ";

    private AutomationInfo info;

    public Button backButton;
    public Label titleLabel;
    public ListGroup<PayStatementYearHeaderListGroupActions> payYearHeaderList;
    public ListGroup<PayStatementListGroupActions> payStatementList; /** First item in the Pay Statement list is offset by 1. Ex: First list item is index(2) */

    public PayStatementPage(AutomationInfo info) {

        this.info = info;
        backButton = new Button(info, By.cssSelector(parentPageSelector + "[data-automation=\"back-button\"]"));
        titleLabel = new Label(info, By.cssSelector(parentPageSelector + "ion-title[data-automation=\"pay-statements-title\"]"));
        payYearHeaderList = new ListGroup<>(info, By.cssSelector("[data-automation=\"pay-statement-list\"]"), new PayStatementYearHeaderListGroupActions());

    }

    public ListGroup<PayStatementListGroupActions> yearlyPayListAtIndex(Integer index) {

        return new ListGroup<PayStatementListGroupActions>(info,
                By.cssSelector(String.format("[data-automation=\"pay-statement-yearly-list\"]:nth-of-type(%s)", index)),
                        new PayStatementListGroupActions());

    }

}
