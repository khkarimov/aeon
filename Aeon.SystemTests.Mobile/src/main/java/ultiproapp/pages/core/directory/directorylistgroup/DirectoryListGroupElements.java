package ultiproapp.pages.core.directory.directorylistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class DirectoryListGroupElements extends ListGroupElements {

    public Button item;
    public Label name;
    public Label title;

    public DirectoryListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        item = new Button(info, selector.toJQuery().find("ion-item"));
        name = new Label(info, selector.toJQuery().find("[data-automation=\"employee-name\"]"));
        title = new Label(info, selector.toJQuery().find("[data-automation=\"employee-title\"]"));

    }

}
