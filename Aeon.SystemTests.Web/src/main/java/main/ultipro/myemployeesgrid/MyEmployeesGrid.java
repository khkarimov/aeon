package main.ultipro.myemployeesgrid;

import aeon.core.testabstraction.elements.web.Grid;

/**
 * Model for the employees grid.
 */
public class MyEmployeesGrid extends Grid<MyEmployeesHeaders> {

    /**
     * Constructor.
     *
     * @param rowBy The corresponding Grid Headers object.
     */
    public MyEmployeesGrid(MyEmployeesHeaders rowBy) {
        super(rowBy);
    }
}
