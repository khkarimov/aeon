package ultiproapp.pages.core.directory.directoryfilterbarlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DirectoryFilterBarListGroupElements extends ListGroupElements {

    public Label label;
    public Button x;

    public DirectoryFilterBarListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        label = new Label(info, selector.toJQuery().find("ion-label[data-automation=\"label\"]"));
        x = new Button(info, selector.toJQuery().find("ion-chip-button"));
    }

}
