package aeon.core.testabstraction.elements.web;

/**
 * Created by AdamC on 4/13/2016.
 */
public abstract class Grid<T extends RowActions>{
    public T RowBy;
    
    public Grid(T rowBy) {
        this.RowBy = rowBy;
    }
}
