package ultiproapp.pages.core.pay.paystatementyearheaderlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class PayStatementYearHeaderListGroupElements extends ListGroupElements {

    public Label payYearHeader;

    public PayStatementYearHeaderListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        payYearHeader = new Label(info, selector.toJQuery().find("[data-automation=\"pay-year\"]"));

    }

}
