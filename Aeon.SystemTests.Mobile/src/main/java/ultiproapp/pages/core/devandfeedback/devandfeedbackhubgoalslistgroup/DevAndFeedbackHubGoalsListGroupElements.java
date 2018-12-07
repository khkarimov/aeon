package ultiproapp.pages.core.devandfeedback.devandfeedbackhubgoalslistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DevAndFeedbackHubGoalsListGroupElements extends ListGroupElements {

    public Label goalProgress;
    public Label goalTitle;

    public DevAndFeedbackHubGoalsListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        goalProgress = new Label(info, selector.toJQuery().find("[data-automation=\"goal-progress-label\"]"));
        goalTitle = new Label(info, selector.toJQuery().find("[data-automation=\"goal-title-label\"]"));

    }

}
