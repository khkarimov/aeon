package aeon.core.framework.abstraction.drivers;

import aeon.core.framework.abstraction.adapters.IAdapter;

import java.awt.*;

/**
 * Created by DionnyS on 4/20/2016.
 */
public interface IDriver {

    IDriver configure(IAdapter adapter);

    void close();

    void quit();

    String getSource();

    Image getScreenshot();
}
