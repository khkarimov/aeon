package main.ultipro.myemployeesgrid;

import com.ultimatesoftware.aeon.core.common.web.selectors.By;
import com.ultimatesoftware.aeon.core.testabstraction.elements.web.Table;

/**
 * Class for finding rows based on certain column values.
 */
public class EmployeeTable extends Table<EmployeeTable, Employee> {

    /**
     * Constructor.
     */
    public EmployeeTable() {
        super(EmployeeTable.class, Employee.class);
    }

    /**
     * Finds a row based on the employee number.
     *
     * @param value The number of the employee to search for in the employee number column.
     * @return An instance of the {@link EmployeeTable} class to support chaining and filtering by multiple columns.
     */
    public EmployeeTable employeeNumber(String value) {
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(2)"));
    }

    /**
     * Finds a row based on the primary work phone number.
     *
     * @param value The primary work phone number of the employee to search for in the primary work phone number column.
     * @return An instance of the {@link EmployeeTable} class to support chaining and filtering by multiple columns.
     */
    public EmployeeTable primaryWorkPhone(String value) {
        return findRow(value, By.cssSelector("#GridView1_colHeaders > th:nth-child(3)"));
    }
}
