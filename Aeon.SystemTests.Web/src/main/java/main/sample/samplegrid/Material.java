package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Button;
import aeon.core.testabstraction.elements.web.Label;
import aeon.core.testabstraction.models.Component;

/**
 * Models the elements of a table row.
 */
public class Material extends Component {
    public Button checkBoxButton;
    public Label unitPrice;

    /**
     * Constructor.
     *
     * @param info            The automation info object to use.
     * @param selector        The selector that identifies the row of the table.
     * @param switchMechanism The switchMechanism to use.
     */
    public Material(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        checkBoxButton = new Button(info, selector.toJQuery().find("span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center"));
        unitPrice = new Label(info, selector.toJQuery().find("td:nth-child(4)"));
    }
}
