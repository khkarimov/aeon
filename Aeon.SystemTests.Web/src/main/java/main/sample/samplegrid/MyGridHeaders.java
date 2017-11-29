package main.sample.samplegrid;

import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.TableActions;

/**
 * Class for finding table rows based on column values.
 */
public class MyGridHeaders extends TableActions<MyGridHeaders, MyGridRowElements> {

    /**
     * Constructor.
     */
    public MyGridHeaders() {
        super(MyGridHeaders.class, MyGridRowElements.class);
    }

    /**
     * Find a row based on a certain value in the "Material" column of the table.
     * @param value The value to look for in the "Material" column of the table.
     * @return An instance of the {@link MyGridHeaders} to support chaining and filtering by multiple columns.
     */
    public MyGridHeaders material(String value) {
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Material)"));
    }

    /**
     * Find a row based on a certain value in the "Quantity" column of the table.
     * @param value The value to look for in the "Quantity" column of the table.
     * @return An instance of the {@link MyGridHeaders} to support chaining and filtering by multiple columns.
     */
    public MyGridHeaders quantity(String value){
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Quantity)"));
    }

    /**
     * Find a row based on a certain value in the "Unit Price" column of the table.
     * @param value The value to look for in the "Unit Price" column of the table.
     * @return An instance of the {@link MyGridHeaders} to support chaining and filtering by multiple columns.
     */
    public MyGridHeaders unitPrice(String value){
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Unit price)"));
    }
}
