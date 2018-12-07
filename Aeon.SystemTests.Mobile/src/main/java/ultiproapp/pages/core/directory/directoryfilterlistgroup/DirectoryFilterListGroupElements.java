package ultiproapp.pages.core.directory.directoryfilterlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;
import aeon.core.testabstraction.elements.web.TextBox;

public class DirectoryFilterListGroupElements extends ListGroupElements {

    public Label label;
    public Label sublabel;
    public TextBox input;

    public DirectoryFilterListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        label = new Label(info, selector.toJQuery().find("h2[data-automation=\"title\"]"));
        sublabel = new Label(info, selector.toJQuery().find("p"));
        input = new TextBox(info, selector.toJQuery().find("ion-input"));

    }

}
