package main.ultipro.myemployeesgrid;

import aeon.core.testabstraction.elements.web.Grid;

/**
 * Created by SebastianR on 12/29/2016.
 */
public class MyEmployeesGrid extends Grid<MyEmployeesHeaders> {

    public MyEmployeesGrid(MyEmployeesHeaders rowBy) {
        super(rowBy);
    }
}
