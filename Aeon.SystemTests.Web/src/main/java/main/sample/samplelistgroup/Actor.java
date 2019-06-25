package main.sample.samplelistgroup;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.models.Component;

/**
 * Models the elements of an item of the list component.
 */
public class Actor extends Component {

    public final Label name;
    public final Label description;

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies the item of the list group.
     * @param switchMechanism The switchMechanism to use.
     */
    public Actor(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
        name = new Label(automationInfo, selector.toJQuery().find("span.list-group-name"));
        description = new Label(automationInfo, selector.toJQuery().find("span.mdl-list__item-text-body"));
    }
}
