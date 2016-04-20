package echo.core.framework_abstraction;

import com.sun.glass.ui.Size;
import echo.core.common.exceptions.*;
import echo.core.common.web.interfaces.IBy;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Created by DionnyS on 4/13/2016.
 */
public interface IAdapter {
    IAdapter Configure(Configuration configuration);
}