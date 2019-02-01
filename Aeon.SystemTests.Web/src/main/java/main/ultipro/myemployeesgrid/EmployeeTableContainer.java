package main.ultipro.myemployeesgrid;

import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.EmployeeTable;

/**
 * Class for finding rows based on certain column values.
 */
public class EmployeeTableContainer extends EmployeeTable<EmployeeTableContainer, Employee> {

    /**
     * Constructor.
     */
    public EmployeeTableContainer() {
        super(EmployeeTableContainer.class, Employee.class);
    }

    /**
     * Finds a row based on the employee number.
     *
     * @param value The number of the employee to search for in the employee number column.
     * @return An instance of the {@link EmployeeTableContainer} class to support chaining and filtering by multiple columns.
     */
    public EmployeeTableContainer employeeNumber(String value) {
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(2)"));
    }

    /**
     * Finds a row based on the primary work phone number.
     *
     * @param value The primary work phone number of the employee to search for in the primary work phone number column.
     * @return An instance of the {@link EmployeeTableContainer} class to support chaining and filtering by multiple columns.
     */
    public EmployeeTableContainer primaryWorkPhone(String value) {
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(3)"));
    }
}
