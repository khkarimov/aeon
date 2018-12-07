package ultiproapp.pages.core.pay.payhubtaxeslistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class PayHubTaxesListGroupElements extends ListGroupElements {

    public Label taxFormType;
    public Label taxFormInfo;

    public PayHubTaxesListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        taxFormType = new Label(info, selector.toJQuery().find("[data-automation=\"tax-form-type\"]"));
        taxFormInfo = new Label(info, selector.toJQuery().find("[data-automation=\"tax-form-info\"]"));

    }

}
