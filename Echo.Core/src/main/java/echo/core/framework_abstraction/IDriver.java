package echo.core.framework_abstraction;

import java.awt.*;
import java.net.URL;
import java.util.UUID;

/**
 * Created by DionnyS on 4/20/2016.
 */
public interface IDriver {
    void Close(UUID guid);

    void Quit(UUID guid);

    String GetSource();

    Image GetScreenshot();
}
