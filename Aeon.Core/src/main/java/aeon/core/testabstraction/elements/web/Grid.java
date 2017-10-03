package aeon.core.testabstraction.elements.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The class to model grid elements.
 *
 * @param <T> the grid row action class for this grid model.
 * @deprecated Please use {@link ListGroup} or {@link Table} instead.
 */
public abstract class Grid<T extends RowActions> {

    public T rowBy;

    static Logger log = LogManager.getLogger(Grid.class);

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param rowBy The rowBy
     */
    public Grid(T rowBy) {
        log.warn("DEPRECATED: The use of the \"Grid\" elements" +
                "has been deprecated and will be removed in future" +
                "versions of Aeon. Please use \"ListGroup\" or \"Table\"" +
                "elements instead");
        this.rowBy = rowBy;
    }
}
