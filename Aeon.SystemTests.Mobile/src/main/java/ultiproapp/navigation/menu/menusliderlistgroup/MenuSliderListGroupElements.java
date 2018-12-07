package ultiproapp.navigation.menu.menusliderlistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class MenuSliderListGroupElements extends ListGroupElements {

    public Label label;

    public MenuSliderListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        label = new Label(info, selector.toJQuery().find("ion-label[class*=\"ion-label\"]"));

    }

}
