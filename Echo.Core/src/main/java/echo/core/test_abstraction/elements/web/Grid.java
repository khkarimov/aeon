package echo.core.test_abstraction.elements.web;

import java.util.Collections;

/**
 * Created by AdamC on 4/13/2016.
 */
public class Grid<T extends RowElements> {
    public final T RowBy;

    public Grid() {
        RowBy = (T) new Object();
    }
}
