package aeon.core.framework.abstraction.drivers;

import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.testabstraction.product.Configuration;

import java.awt.*;

/**
 * The base interface for drivers.
 */
public interface IDriver {

    /**
     * Sets the adapter for the current driver.
     *
     * @param adapter The new adapter for the driver.
     * @param configuration The configuration used for the driver.
     * @return The driver after setting the adapter.
     */
    IDriver configure(IAdapter adapter, Configuration configuration);

    /**
     * Close the current window, quitting the browser if it's the last window currently open.
     */
    void close();

    /**
     * Quits this driver, closing every associated window.
     */
    void quit();

    /**
     * Gets an {@link String} object with the source of the focused browser window.
     *
     * @return The source of the current window.
     */
    String getSource();

    /**
     * Gets an {@link Image} screenshot of the focused browser window.
     *
     * @return The screenshot of the current window.
     */
    Image getScreenshot();
}
