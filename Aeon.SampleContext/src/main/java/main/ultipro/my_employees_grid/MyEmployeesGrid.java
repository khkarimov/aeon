package main.ultipro.my_employees_grid;

import echo.core.test_abstraction.elements.web.Grid;

/**
 * Created by SebastianR on 12/29/2016.
 */
public class MyEmployeesGrid extends Grid<MyEmployeesHeaders> {

    public MyEmployeesGrid(MyEmployeesHeaders rowBy) {
        super(rowBy);
    }
}
