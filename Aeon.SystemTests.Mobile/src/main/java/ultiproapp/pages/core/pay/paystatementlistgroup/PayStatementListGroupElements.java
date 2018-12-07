package ultiproapp.pages.core.pay.paystatementlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class PayStatementListGroupElements extends ListGroupElements {

    public Label netPay;
    public Label totalHours;
    public Label companyName;
    public Label date;

    public PayStatementListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        netPay = new Label(info, selector.toJQuery().find("[data-automation=\"net-pay\"]"));
        totalHours = new Label(info, selector.toJQuery().find("[data-automation=\"pay-total-hours\"]"));
        companyName = new Label(info, selector.toJQuery().find("[data-automation=\"company-name\"]"));
        date = new Label(info, selector.toJQuery().find("[data-automation=\"pay-date\"]"));

    }

}
