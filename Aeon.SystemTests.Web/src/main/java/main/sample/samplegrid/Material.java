package main.sample.samplegrid;

import com.ultimatesoftware.aeon.core.command.execution.AutomationInfo;
import com.ultimatesoftware.aeon.core.common.web.interfaces.IByWeb;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Button;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Label;
import com.ultimatesoftware.aeon.core.testabstraction.models.Component;

/**
 * Models the elements of a table row.
 */
public class Material extends Component {
    public final Button checkBoxButton;
    public final Label unitPrice;

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies the row of the table.
     * @param switchMechanism The switchMechanism to use.
     */
    public Material(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
        checkBoxButton = new Button(automationInfo, selector.toJQuery().find("span.mdl-checkbox__ripple-container.mdl-js-ripple-effect.mdl-ripple--center"));
        unitPrice = new Label(automationInfo, selector.toJQuery().find("td:nth-child(4)"));
    }
}
