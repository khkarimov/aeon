package aeon.core.framework.abstraction.drivers;

import aeon.core.framework.abstraction.adapters.IAdapter;

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
