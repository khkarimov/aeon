package ultiproapp.pages.core.pay.payhubpaystatementlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class PayHubPayStatementListGroupElements extends ListGroupElements {

    public Label netPay;
    public Label totalHours;
    public Label date;

    public PayHubPayStatementListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        netPay = new Label(info, selector.toJQuery().find("[data-automation=\"net-pay\"]"));
        totalHours = new Label(info, selector.toJQuery().find("[data-automation=\"pay-total-hours\"]"));
        date = new Label(info, selector.toJQuery().find("[data-automation=\"pay-date\"]"));

    }

}
