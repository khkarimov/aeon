package main.sample.samplegrid;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.web.interfaces.IBy;
import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.RowActions;
import aeon.core.testabstraction.elements.web.TableActions;

public class MyGridHeaders extends TableActions<MyGridHeaders, MyGridRowElements> {
    public MyGridHeaders() {
        super(MyGridHeaders.class, MyGridRowElements.class);
    }

    public MyGridHeaders material(String value) {
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Material)"));
    }

    public MyGridHeaders quantity(String value){
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Quantity)"));
    }

    public MyGridHeaders unitPrice(String value){
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Unit price)"));
    }
}
