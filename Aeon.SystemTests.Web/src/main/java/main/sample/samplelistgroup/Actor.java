package main.sample.samplelistgroup;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.models.Component;

/**
 * Models the elements of an item of the list component.
 */
public class Actor extends Component {
    public Label name;
    public Label description;

    /**
     * Constructor.
     *
     * @param info            The automation info object to use.
     * @param selector        The selector that identifies the item of the list group.
     * @param switchMechanism The switchMechanism to use.
     */
    public Actor(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        name = new Label(info, selector.toJQuery().find("span.list-group-name"));
        description = new Label(info, selector.toJQuery().find("span.mdl-list__item-text-body"));
    }
}
