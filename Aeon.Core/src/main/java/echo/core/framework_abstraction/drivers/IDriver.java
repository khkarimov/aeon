package echo.core.framework_abstraction.drivers;

import echo.core.framework_abstraction.adapters.IAdapter;

import java.awt.*;
import java.util.UUID;

/**
 * Created by DionnyS on 4/20/2016.
 */
public interface IDriver {
    IDriver Configure(IAdapter adapter);

    void Close(UUID guid);

    void Quit(UUID guid);

    String GetSource();

    Image GetScreenshot();
}
