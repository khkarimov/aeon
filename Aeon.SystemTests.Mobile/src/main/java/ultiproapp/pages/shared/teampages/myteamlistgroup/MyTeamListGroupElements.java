package ultiproapp.pages.shared.teampages.myteamlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class MyTeamListGroupElements extends ListGroupElements {

    public Button button;
    public Button avatar;
    public Label employeeName;
    public Label employeeTitle;

    public MyTeamListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        button = new Button(info, selector.toJQuery().find("ion-item"));
        avatar = new Button(info, selector.toJQuery().find("upa-person-avatar"));
        employeeName = new Label(info, selector.toJQuery().find("[data-automation=\"employee-name\"]"));
        employeeTitle = new Label(info, selector.toJQuery().find("[data-automation=\"employee-title\"]"));

    }

}
