package aeon.core.testabstraction.elements.web;

/**
 * The class to model grid elements.
 *
 * @param <T> the grid row action class for this grid model.
 */
public abstract class Grid<T extends RowActions> {

    public T RowBy;

    /**
     * Creates a new instance of {@link Button}.
     *
     * @param rowBy The rowBy
     */
    public Grid(T rowBy) {
        this.RowBy = rowBy;
    }
}
