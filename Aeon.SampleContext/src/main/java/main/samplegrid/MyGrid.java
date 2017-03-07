package main.samplegrid;

import aeon.core.test_abstraction.elements.web.Grid;

/**
 * Created by justinp on 12/20/16.
 */
public class MyGrid extends Grid<MyGridHeaders> {
    public MyGrid(MyGridHeaders gridHeaders) {
        super(gridHeaders);
    }
}
