package main.sample.samplegrid;

import aeon.core.common.web.selectors.By;
import aeon.core.testabstraction.elements.web.Table;
import aeon.core.testabstraction.elements.web.TableContainer;

/**
 * Class for finding table rows based on column values.
 */
public class MaterialTable extends Table<MaterialTable, Material> {

    /**
     * Constructor.
     */
    public MaterialTable() {
        super(MaterialTable.class, Material.class);
    }

    /**
     * Find a row based on a certain value in the "Material" column of the table.
     *
     * @param value The value to look for in the "Material" column of the table.
     * @return An instance of the {@link MaterialTable} to support chaining and filtering by multiple columns.
     */
    public MaterialTable material(String value) {
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Material)"));
    }

    /**
     * Find a row based on a certain value in the "Quantity" column of the table.
     *
     * @param value The value to look for in the "Quantity" column of the table.
     * @return An instance of the {@link MaterialTable} to support chaining and filtering by multiple columns.
     */
    public MaterialTable quantity(String value) {
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Quantity)"));
    }

    /**
     * Find a row based on a certain value in the "Unit Price" column of the table.
     *
     * @param value The value to look for in the "Unit Price" column of the table.
     * @return An instance of the {@link MaterialTable} to support chaining and filtering by multiple columns.
     */
    public MaterialTable unitPrice(String value) {
        return findRow(value, By.cssSelector("#grid-table-id th:contains(Unit price)"));
    }
}
