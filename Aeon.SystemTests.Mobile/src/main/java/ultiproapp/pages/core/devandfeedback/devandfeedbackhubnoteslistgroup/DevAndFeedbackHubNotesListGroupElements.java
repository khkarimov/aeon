package ultiproapp.pages.core.devandfeedback.devandfeedbackhubnoteslistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DevAndFeedbackHubNotesListGroupElements extends ListGroupElements {

    public Label creatorName;
    public Label comment;
    public Label date;

    public DevAndFeedbackHubNotesListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        creatorName = new Label(info, selector.toJQuery().find("[data-automation=\"note-label\"] h2"));
        comment = new Label(info, selector.toJQuery().find("[data-automation=\"note-label\"] p"));
        date = new Label(info, selector.toJQuery().find("[data-automation=\"note-date\"]"));

    }

}
