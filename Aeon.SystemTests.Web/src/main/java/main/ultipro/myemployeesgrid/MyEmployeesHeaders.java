package main.ultipro.myemployeesgrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IByWeb;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.RowActions;

/**
 * Class for finding rows based on certain column values.
 */
public class MyEmployeesHeaders extends RowActions<MyEmployeesHeaders, MyEmployeesRowElements> {

    /**
     * Constructor.
     *
     * @param automationInfo The automation info object to use.
     * @param selector The selector that identifies the grid.
     * @param switchMechanism The switchMechanism to use.
     */
    public MyEmployeesHeaders(AutomationInfo automationInfo, IByWeb selector, Iterable<IByWeb> switchMechanism) {
        super(automationInfo, selector, switchMechanism, MyEmployeesHeaders.class, MyEmployeesRowElements.class);
    }

    /**
     * Finds a row based on the employee number.
     *
     * @param value The number of the employee to search for in the employee number column.
     * @return An instance of the {@link MyEmployeesHeaders} class to support chaining and filtering by multiple columns.
     */
    public MyEmployeesHeaders employeeNumber(String value){
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(2)"));
    }

    /**
     * Finds a row based on the primary work phone number.
     *
     * @param value The primary work phone number of the employee to search for in the primary work phone number column.
     * @return An instance of the {@link MyEmployeesHeaders} class to support chaining and filtering by multiple columns.
     */
    public MyEmployeesHeaders primaryWorkPhone(String value) {
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(3)"));
    }
}
