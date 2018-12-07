package ultiproapp.pages.core.devandfeedback.devandfeedbackhubcompetencieslistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DevAndFeedbackHubCompetenciesListGroupElements extends ListGroupElements {

    public Label competencyTitle;

    public DevAndFeedbackHubCompetenciesListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        competencyTitle = new Label(info, selector.toJQuery().find("[data-automation=\"competency-title\"]"));

    }

}
