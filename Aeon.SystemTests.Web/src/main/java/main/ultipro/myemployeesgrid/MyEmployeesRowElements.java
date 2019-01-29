package main.ultipro.myemployeesgrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.testabstraction.elements.web.Component;
import aeon.core.testabstraction.elements.web.Link;

/**
 * Class for modeling the elements of a row in the employees grid.
 */
public class MyEmployeesRowElements extends Component {
    public Link employeeLink;

    /**
     * Constructor.
     *
     * @param info            The automation info object to use.
     * @param selector        The selector that identifies the grid row.
     * @param switchMechanism The switchMechanism to use.
     */
    public MyEmployeesRowElements(AutomationInfo info, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(info, selector, switchMechanism);
        employeeLink = new Link(info, selector.toJQuery().find("#ctl00_Content_GridView1_ctl02_clDetail"), switchMechanism);
    }
}
