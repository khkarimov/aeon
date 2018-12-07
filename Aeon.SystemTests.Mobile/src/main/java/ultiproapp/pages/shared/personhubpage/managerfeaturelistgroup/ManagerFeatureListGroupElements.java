package ultiproapp.pages.shared.personhubpage.managerfeaturelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Image;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class ManagerFeatureListGroupElements extends ListGroupElements {

    public Image icon;
    public Label featureHeading;

    public ManagerFeatureListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        icon = new Image(info, selector.toJQuery().find("ion-icon"));
        featureHeading = new Label(info, selector.toJQuery().find("[data-automation=\"manager-feature-label\"] h2"));

    }

}
