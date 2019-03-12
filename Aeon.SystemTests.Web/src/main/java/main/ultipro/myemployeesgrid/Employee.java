package main.ultipro.myemployeesgrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Link;
import aeon.core.testabstraction.models.Component;

/**
 * Class for modeling the elements of a row in the employees grid.
 */
public class Employee extends Component {
    
    public Link employeeLink;

    /**
     * Constructor.
     *
     * @param automationInfo  The automation info object to use.
     * @param selector        The selector that identifies the grid row.
     * @param switchMechanism The switchMechanism to use.
     */
    public Employee(AutomationInfo automationInfo, IByWeb selector, IByWeb... switchMechanism) {
        super(automationInfo, selector, switchMechanism);
        employeeLink = new Link(automationInfo, selector.toJQuery().find("#ctl00_Content_GridView1_ctl02_clDetail"), switchMechanism);
    }
}
