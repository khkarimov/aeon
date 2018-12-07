package ultiproapp.pages.shared.teampages.teamfeaturelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Image;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.elements.web.ListGroupElements;

public class TeamFeatureListGroupElements extends ListGroupElements {

    public Image icon;
    public Label featureHeading;
    public Label featureSubHeading;

    public TeamFeatureListGroupElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {

        super(info, selector, switchMechanism);
        icon = new Image(info, selector.toJQuery().find("[data-automation=\"team-feature-icon\"]"));
        featureHeading = new Label(info, selector.toJQuery().find("[data-automation=\"team-feature-label\"] h2"));
        featureSubHeading = new Label(info, selector.toJQuery().find("[data-automation=\"team-feature-label\"] p"));

    }

}
