package ultiproapp.pages.core.pay.paystatementdetailssummarylistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class PayStatementDetailsSummaryListGroupElements extends ListGroupElements {

    public Label title;
    public Label value;

    public PayStatementDetailsSummaryListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        title = new Label(info, selector.toJQuery().find("ion-label p"));
        value = new Label(info, selector.toJQuery().find("ion-label h2"));

    }

}
