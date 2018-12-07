package ultiproapp.pages.core.directory.directoryradiolistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DirectoryRadioListGroupElements extends ListGroupElements {

    public Label label;

    public DirectoryRadioListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        label = new Label(info, selector.toJQuery().find("[data-automation=\"label\"]"));

    }

}
