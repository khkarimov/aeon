package main.ultipro.myemployeesgrid;

import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.ComponentTable;
import aeon.core.testabstraction.models.Component;

/**
 * Class for finding rows based on certain column values.
 */
public class MyEmployeesHeaders extends ComponentTable<MyEmployeesHeaders, Component> {

    /**
     * Constructor.
     */
    public MyEmployeesHeaders() {
        super(MyEmployeesHeaders.class, Component.class);
    }

    /**
     * Finds a row based on the employee number.
     *
     * @param value The number of the employee to search for in the employee number column.
     * @return An instance of the {@link MyEmployeesHeaders} class to support chaining and filtering by multiple columns.
     */
    public MyEmployeesHeaders employeeNumber(String value) {
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
