package echo.core.test_abstraction.elements.web;

import echo.core.common.web.interfaces.IByJQuery;

/**
 * Created by AdamC on 4/13/2016.
 */
public class RowElements {
    public final RowActions Name;

    public RowElements() {
        Name = new RowActions(null, new IByJQuery("#grid_header_name"));
    }
}
